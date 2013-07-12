var Amplitude = Class(function () {
	this.track = function (name, data) {
		logger.log("{amplitude} track: ", name, JSON.stringify(data));

		if (NATIVE && NATIVE.plugins && NATIVE.plugins.sendEvent) {
			NATIVE.plugins.sendEvent("AmplitudePlugin", "track",
				JSON.stringify({ eventName: name, params: data }));
		}
	};
});

exports = new Amplitude();
