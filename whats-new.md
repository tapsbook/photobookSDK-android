## Update of Tapsbook SDK for Android

If you run into any issues, please contact help at tapsbook.com

July 15 Build 106
- [editing] add feature to show a photo list as placeholder to show unused book photos.
- [editing] add feature to support delete photo from a page via drag and drop (long hold an image to trigger)
- [editing] support SDK customer to provide a product logo on the printed output.
- [bug fixes] fixed bugs related to rearrange page order (drag drop on multi-page view)
- [bug fixes] fixed bug related to swipe to change page layout feature
- [performance tuning] fixed various problems causing excessive memory usage.

Jul 7 Build 105
- [core] revamped page generation engine w/ new algorithms
- [editing] add feature to support rearrange page order via drag and drop page. To use it, click multi-page view button (bottom left button), then long hold a page to arrange.
- [new API] photo book listing API that supports retrieve a list of existing photo books.
- [bug fixes] fixed page number label bug
- known issue: the JSON output data from the previous build was temperarily disabled as we refactor the model layer, coming back in the next build.
- Known issue: the drag and drop feature has compatibility issue when run on GenyMotion. please use real android device to test drag and drop feature.

Jun 28 Build 104
- [editing] add feature to support photo edit: crop, scale and resize
- [editing] add feature to support photo rotate
- [output] generate screen size images per page for devOps & automated test
- [bug-fix] fixed compatibility issue to support Android 4.1

Jun 22 Build 103
- [output] add JSON raw data as output format
- [output] change SDK return type to JSON in its callback

Jun 16 Build 102
- [output] render pixel perfect output images and persist it locally
- [upload] upload page rendered result to Cloud storage, show progress bar and support auto-resume when interrupted
- [edit UI] refactor book cover rendering
- [edit UI] fixed the transition from cover to pages
- [eCommerce] added a provider callback to Babytree ecommerce system, see the repo's readme installation section.

Jun 7 Build 101
- [Input] create a book using an ordered set of images as input
- [template] create book pages w/ pre-determined page template data from DB
- [editing] change page layout via our patented swipe to change layout gesture. Notice not all editing function found in iOS are available in this build. 
- [zoom-in/out] offer multiple zoom mode so that user can quickly view all book pages and adjust order
