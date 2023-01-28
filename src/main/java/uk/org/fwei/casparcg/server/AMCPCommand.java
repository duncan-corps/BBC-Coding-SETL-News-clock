package uk.org.fwei.casparcg.server;

public class AMCPCommand {

	private static StringBuilder cg(final int channel, final Integer layer) {
		final StringBuilder stringBuilder = new StringBuilder("CG ");
		stringBuilder.append(channel);

		if (layer != null) {
			stringBuilder.append('-').append(layer);
		}

		stringBuilder.append(' ');

		return stringBuilder;
	}

	// CG [video_channel:int]{-[layer:int]|-9999} ADD [cg_layer:int]
	// [template:string] [play-on-load:0,1] {[data]}
	public static String cgAdd(final int channel, final Integer layer, final int cgLayer, final String template,
			final boolean playOnLoad, final String data) {
		final StringBuilder amcpCommand = cg(channel, layer);
		amcpCommand.append("ADD ").append(cgLayer).append(' ').append(template).append(' ').append(playOnLoad ? 1 : 0);

		if (data != null && !data.isBlank()) {
			amcpCommand.append(data);
		}

		return amcpCommand.toString();
	}

	public static String cgAdd(final int channel, final int cgLayer, final String template, final boolean playOnLoad,
			final String data) {
		final Integer layer = null;
		return cgAdd(channel, layer, cgLayer, template, playOnLoad, data);
	}

	// CG [video_channel:int]{-[layer:int]|-9999} INVOKE [cg_layer:int]
	// [method:string]
	public static String cgInvoke(final int channel, final Integer layer, final int cgLayer, final String method) {
		final StringBuilder amcpCommand = cg(channel, layer);
		amcpCommand.append("INVOKE ").append(cgLayer).append(" \"").append(method).append('"');

		return amcpCommand.toString();
	}

	public static String cgInvoke(final int channel, final int cgLayer, final String method) {
		final Integer layer = null;
		return cgInvoke(channel, layer, cgLayer, method);
	}

}
