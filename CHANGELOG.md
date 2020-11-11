
# Change Log

## Version 1.0.7

Release: Nov 11, 2020

Changes

* Providing optional method on CommerceConfig to set CheckoutButton image by Merchant App

## Version 1.0.6

Release: Aug 06, 2020

Changes

* Support for $0 transaction on Commerce SDK

## Version 1.0.5

Release: June 26, 2020

Changes

* Refactored AsyncTask background to ThreadPoolExecutor
* Fixed an issue for App crash reported during network connectivity failure

## Version 1.0.4

Release: February 20, 2020

Changes

* Enabled dynamic rendering of Webviews
* Fixed an issue causing Feedback and Profile pages to open in mobile browser
* Fixed an issue to hide progress bar when returning from Terms & Conditions or Privacy Policy pages
* Removed erroneous dependency on card-io library 

## Version 1.0.3

Release: November 20, 2019

Changes

* Added Voice Over support for the checkout button
* Fixed an issue causing the wrong checkout button image to download
* Fixed an issue causing the wrong checkout button default image to display

## Version 1.0.2

Release: October 30, 2019

Changes

* Fixed an issue where CheckoutActivity is crashing on Oreo
* Fixed an issue where CheckoutActivity remained in the Activity stack after merchant callback is initiated 

## Version 1.0.1

Release: October 11, 2019

Changes

* Fixed an issue causing SSL handshake to fail sometimes when using an emulator
* Fixed an issue where the popup window was not getting destroyed after removing it from the view
* Fixed the color value for No Network Connectivity error dialog
* Moved dependency for SVG rendering to external library
* Added logging parameters in the checkout URL`
* Updated dependency versions

## Version 1.0.0

Released: August 9, 2019

Changes:

* Releasing 1.0.0

## Version 1.0.0-beta3

Released: August 7, 2019

Changes:

* Enable emulator testing
* Remove zoom control overlays on WebView
* Add progress dialog to popup WebView
* Fix issue where WebViews persist after Activity finishes
* Implement warning for no network connectivity
* Update CheckoutCallback to pass a listener for asynchronous responses


## Version 1.0.0-beta2

Released: July 31, 2019

Changes:

* Deprecate `masterpass-merchant` classes

## Version 1.0.0-beta1

Released: July 15, 2019

Changes:

* Initial beta release