// https://github.com/amplitude/Amplitude-Javascript

(function(e,t){var r=e.amplitude||{};var a=t.createElement("script");a.type="text/javascript";
a.async=true;a.src="https://d24n15hnbwhuhn.cloudfront.net/libs/amplitude-1.0-min.js";
var n=t.getElementsByTagName("script")[0];n.parentNode.insertBefore(a,n);
r._q=[];function i(e){r[e]=function(){r._q.push([e].concat(Array.prototype.slice.call(arguments,0)))}}
var s=["init","logEvent","setUserId","setGlobalUserProperties","setVersionName"];
for(var c=0;c<s.length;c++){i(s[c])}e.amplitude=r})(window,document);

;(function(){var c=CONFIG.addons.amplitude;if(c.apiKey){amplitude.init(c.apiKey);amplitude.logEvent("PAGE_LOAD_START");var s=0;__amplitude={start:Date.now(),timer:setInterval(function(){s+=2;amplitude.logEvent("PAGE_LOADING_"+s)},2000)}}})();
