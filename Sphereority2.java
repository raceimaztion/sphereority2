/**
 * The main entry class for the client.
 * This gets called from the command-line.
 * @author dvanhumb
 */
public class Sphereority2
{
	private static final String PARAM_SERVER = "-server=";
	private static final String PARAM_NAME = "-user=";
	private static final String PARAM_BOT = "-bot=";
	
	/**
	 * Main entry point for client.
	 * Parameters supported:
	 * -server=(server)  Connect to the specified server
	 * -user=(name)  Use the specified name
	 * -bot=(botai)  Use the specified bot type
	 * @param args
	 */
	public static void main(String[] args)
	{
		// Have we been asked for help?
		for (String p : args)
			if (p.contains("-help"))
			{
				System.out.println("Sphereority 2 - Client");
				System.out.println("  Command-line help: (all parameters are optional)");
				System.out.println("    -server=(server)  Connect to a particular server.");
				System.out.println("    -user=(username)  The player name to use.");
				System.out.println("    -bot=(bot-ai)     The AI to use (human-controlled if not specified).");
				return;
			}
		
		String server=null, user=null, bot_ai=null;
		
		for (String param : args)
		{
			if (param.startsWith(PARAM_SERVER))
				server = param.substring(PARAM_SERVER.length());
			else if (param.startsWith(PARAM_NAME))
				user = param.substring(PARAM_NAME.length());
			else if (param.startsWith(PARAM_BOT))
				bot_ai = param.substring(PARAM_BOT.length());
			else
				System.out.printf("Unknown parameter '%s', ignoring.\n", param);
		}
		
		// TODO: Launch clients
		System.out.printf("Connecting to server %s with username %s, using bot-AI %s\n", server, user, bot_ai);
	}
}
