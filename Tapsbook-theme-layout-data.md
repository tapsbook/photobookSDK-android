## Tapsbook Page Theme

### Overview
Tapsbook uses our own template engine to combine a page template metadata and user supplied images, text, and embellishment to form finished book pages. The template metadata is stored in a SQlite DB. Enterprise Account Developers can also customize their own template to be loaded into their final app.

The template metadata is organized as a tree structure where 

1. The top level node is "Theme" which creates multiple significantly different page styles. The style differs on page ratio and page color tone. When you want to expose multiple themes to your customers, you can present a theme selection UI to your customer. The default theme loaded in the current SDK is theme.id=200
2. Each Theme includes multiple page layouts. Page layout dictate how many photos to be arranged on the page, and whether a page is a standard page or a spread page (a spread page is two standard pages combined into one sheet).
3. Each page layouts include multiple slots, where a slot can be one of the three types: photo slot, text slot and embellishment slot. These slots all have generic properties such as their positions on the page, the content placement relative to the slot etc.

At the run time, when user chooses to auto-generate all book pages, the engine first loads all templates data from a local sqlite database and intelligently match the appropriate page template based on the photo selections 

### Importing existing theme XML to Tapsbook template DB
Note: _this section was created based on our developer experience in working with one of our partners, the script provided here is provided AS-IS, we provide no warranty to this script. If you are enterprise client of Tapsbook patform, we can provide services helping you to import your themes to SDK._

If the 3-tier template structure adopted by Tapsbook is similar to your existing photo book template, then you can use the attached template transform script to batch export your template from XML format into a SQL data that can be directly imported into your SQlite database.  

Concept wise, this transform script is like any XSLT transform that transform one piece of structured  data into another form, here we transform the each XML node into one of the three objects, and from there we can easily convert that object to SQL using the object.to_sql method.

The attached script was developed to transform one of our partner's template XML. your XML might have different schema and node name. You will need to edit the script to ensure the node name pattern that referred is the same as your source XMLs. Here is a piece of code snippet of the source XML. 
```` 
<TemplateList>
	<TemplateListSet>
		<Templates>
			<TemplateInfo>
				<Name>ABC theme</Name>
				<WidthInches>10</WidthInches>
				<HeightInches>8</HeightInches>
				<DropZones>
					<DropZone>
						<X>2</X>
						<Y>2</Y>
					</DropZone>
				</DropZones>
				<Texts>
				</Texts>
			</TemplateInfo>
````
This source XML maps to our template structure well, in that each TemplateInfo is a Page Layout, and it consists of multiple DropZones. Correspondingly, you can find the convert script handles transforming the node by matching patterns such as 
````
templates_doc = doc.xpath('//TemplateListSet/Templates/TemplateInfo')
...
templates_doc.each do |template_node|
  #page layouts
  tb_layout = TBLayout.new
  tb_layout.id = layout_start_id
  layout_width = template_node.xpath('./WidthInches')[0].content.to_f
  layout_height = template_node.xpath('./HeightInches')[0].content.to_f
  #slots
  dropzones_doc = template_node.xpath('./DropZones/DropZone')
  dropzones_doc.each_with_index do |dropzone_node, index|
    tb_slot = TBSlot.new
    tb_slot.id = slot_star_id
    slot_left = dropzone_node.xpath('./X')[0].content.to_f
    slot_top = dropzone_node.xpath('./Y')[0].content.to_f
````  
Finally, modify the path to your XML source
````
config_path = 'PATH_TO_YOUR_XML/Templates.all.xml'
````

Once you touched the script, you can then run it 
````
ruby convert.rb
````
This script will produce three sql script that you can directly import to your sqlite DB.

### Default template and theme
The default page template is stored as TapsbookSDK.bundle\TBTemplate_Default_xx.sqlite. It includes multiple themes for different purpose. 

The sample  SDK demo app currently loads a simple theme (ID=200) as the default template.  You can select another template by setting the following TBSDKConfiguration
````
- (NSInteger)defaultThemeID {
    NSInteger defaultThemeID = 1;
    return defaultThemeID;
}
````

If you have your own template, you can copy it to [NSHomeDirectory()/Library/TBSDK folder, and then use the following method to force SDK load your template vs the default template. You should also implement the defaultThemeID method above that returns a theme ID that matches to the theme ID in your template DB.

````
- (NSString *)templateDatabaseName {
    NSString *templateDatabaseName = @"YOUR_TEMPLATE.sqlite";
    return templateDatabaseName;
}
````

<b>Note: This TBTemplate_Default_xx.sqlite template data is the licensed property of Tapsbook, it is only authorized to be used in conjunction with Tapsbook SDK. You are not allowed to alter it for your own purpose. Nor can you export it and use it outside Tapsbook SDK. Doing so will be an copyright violation. If you want to use your own template. You must create your own template by importing a template from your XML or manually create a new template. </b>

### Template DB attribute reference

When you want to create or edit the template data, you may find the reference table below useful. During the photo book exporting phase (for print or for sharing), we also produce the data in a similar structure.

Slots table correspond to the TBSlot object
````
  layout_id integer,  foreign key point to layout table id
  z_position integer DEFAULT(0),  slot  order when they overlap, small z appear on top
  center_x integer DEFAULT(0),   center x of slot relative to page_w
  center_y integer DEFAULT(0), center x of slot relative to page_h
  width integer DEFAULT(0), width of slot relative to page_w
  height integer DEFAULT(0), height of slot relative to page_h
  rotation integer DEFAULT(0), degree of rotation
  content_width integer DEFAULT(0),  the photo content's w, user may scale the photo, 
  content_height integer DEFAULT(0), see above
  content_offset_x integer DEFAULT(0), the center offset x of the photo content
  content_offset_y integer DEFAULT(0), the center offset y of the photo content
  content_rotation integer DEFAULT(0), degree of content rotation
  content_type integer DEFAULT(0),  specify whether the content is a photo or text, see below
    TBContentType_Image = 1,
    TBContentType_Label = 2,
  text_content text,   text label content
  text_font_size integer, size of font
  text_font_name char(40), font name
  text_foreground_color integer, text color
  text_stroke_color integer, text stroke color
  text_background_color integer, text background color 
  text_stroke_width integer, 
  text_alignment integer, center align by default
  frame_id integer,  border of the slot
  frame_width_or_scale integer, border width
  frame_color integer border color
````

Theme table corresponds to the TBTheme object
````
  name text,   display name of the theme
  std_page_width integer DEFAULT(0),  all book page should have this page_w
  std_page_height integer DEFAULT(0), same as above
  need_inside_cover_pages integer DEFAULT(0), whether there is a non-printable insider cover (a inside cover is the other side of the front cover, it is usually not printable), 1 means need inside cover not printable
  std_ratio_type integer DEFAULT(1),  page ratio between w/h, defined as the following, should be the same as page_layout
    TBStdPageRatio_11x8_5 = 1,
    TBStdPageRatio_10_5x8 = 2,
    TBStdPageRatio_5x4    = 3,
    TBStdPageRatio_1x1    = 4,
  allow_generic_layout integer(1) DEFAULT(0),  not defined, ignore
  prefer_spread integer DEFAULT(0)  1 means this theme want all pages are spread except page 1 and last page are single page due to inside cover, 
  preview_path text,  theme thumbnail image for display, 
 "disabled" INTEGER DEFAULT 0);   disabled=1 means this theme will not show for user in the theme selection UI (Internal)
````

### Exported book page layout data

The exported book page layout data has a structure and semantics very similar to the page template data above. Notice in order to accommodate both web presentation and print, the number here are all relative numbers, they are not pixel count, you will need to scale it to the actual page width in pixel based on your web presentation or print need.

````
                "slots": [
                    {
//content type. this may be a text or photo
                        "content": {
                            "type": "image",
                            "isColor": true,
                            "color": "FFFFFFFF"
                        },
                        "rotation": 0,
//page width and height, use this to find the necessary scale ratio: e.g. if you page height is 8 in *300 dpi, then your page height is 2400, => your scale ratio = 2400/87500, the rest of this slot data needs to apply to the same ratio to find the print page pixel count. Notice w is 2 * h in this example, because this is a spread page.
                        "pageHeight": 87500,      
                        "pageWidth": 177500

//next four number describes the slot position relative to the page, (top left as origin), centerX/Y is the center on the page, width and height is the slot's w/h, in this example, w = pageWidth, so this is a slot occupying the full page.
                        "centerX": 88750,
                        "centerY": 43750,
                        "width": 177500,
                        "height": 87500,

//next four number specifies the offset of the image away from <b>the original centered position</b>. in this case, offsetX/Y=0/0 means the image is centered in the slot. there may be crop at four side.
                        "contentOffsetX": 0,
                        "contentOffsetY": 0,
                        "contentHeight": 87500,
                        "contentWidth": 177500,

                        "backgroundColor": "00000000",
                        "zIndex": -100,
                        "contentRotation": 0,
                    },
````

