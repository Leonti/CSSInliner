#CSSInliner - Utility to inline css styles in html document

By Leonty Belskiy

##Code examples

Css will be merged with provided html:
	String inlinedHtml = CSSInliner.inlineCss(html, css);

If used for email, 'class' attributes can be removed:
	String inlinedHtml = CSSInliner.inlineCss(html, css, true);


##Changes

v1.0 2012-02-05 Leonty Belskiy

* Initial release

##License

CSSInliner
Copyright 2012 Leonty Belskiy, leonti[at]eleonti.com.

Licensed under the Apache License, Version 2.0


