import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Java Player Image URLs program implements an application that generates unique URL for
 * a given set of player Id, league type and image style.
 *
 * @author Bijay Sharma
 * @version 1.0
 * @since 2017-11-30
 */
class PlayerImageUrl {
	public static String SecretValue = "secretkeyvalue";
	public static String CloudFrontBase = "https://d1lor8s1noym9m.cloudfront.net/players";

	/**
	 * This method generates unique URL for a given Player ID, league type, and image style.
	 * It first get the input by concatenating the inputs separated by '-' into a single string.
	 *
	 * @param leagueType This is type of a league.
	 * @param playerId This is an ID of a player.
	 * @param style This is a style of an image.
	 * @return URL for a given Player ID, league type, and image style.
	 *
	 * @throws NoSuchAlgorithmException if the algorithm does not exists.
	 * @throws InvalidKeyException if the input provided is not correct.
	 */

	public static String getPlayerUrl(String leagueType, String playerId, String style)
			throws NoSuchAlgorithmException, InvalidKeyException {
		final String cryptoAlgoName = "HmacSHA1"; // Algorithm Name initialized
		final String inputData = getInputData(leagueType, playerId, style); // Get the data to encode
		final byte[] secretValueBytes = getKeyInBytes(SecretValue); // Get the bytes of the raw key.
		final SecretKeySpec signingKey = getSecretKeySpec(secretValueBytes,cryptoAlgoName); //Get the secret key based on the algorithm specification
		final Mac mac = Mac.getInstance(cryptoAlgoName); //Get the Mac instance
		mac.init(signingKey); //initialize the Mac instance
		final byte[] inputDataBytes = getKeyInBytes(inputData);  //Get the bytes of the raw inputData
		final String hexadecimalHmacResult = getHexadecimalEncode(mac.doFinal(inputDataBytes)); //Get the hexadecimal result from hmac.
		return getFinalPlayerUrl(leagueType, style, hexadecimalHmacResult); //return encoded URL.
	}


	/**
	 * This method returns an array of bytes for a parameter.
	 *
	 * @param input is a parameter to be returned as an array of byte.
	 * @return returns an byte array for an input string
	 *
	 * @throws InvalidKeyException if the input provided is not valid.
	 */
	private static byte[] getKeyInBytes(final String input) throws InvalidKeyException {
		if (!isValidArgument(input))
			throw new InvalidKeyException("Provided key is either not provided or blank");
		return input.getBytes();
	}

	/**
	 * This method returns a secret key depending on the algorithm specification.
	 *
	 * @param secretValueBytes It is bytes of a secret value.
	 * @param cryptoAlgoName signifies an algorithm specification.
	 * @return a {@link SecretKeySpec} secret key based on the specified algorithm and byte value of provided secret value.
	 *
	 * @throws NoSuchAlgorithmException if the algorithm provided does not exist.
	 */
	private static SecretKeySpec getSecretKeySpec(final byte[] secretValueBytes, final String cryptoAlgoName) throws NoSuchAlgorithmException{
		return new SecretKeySpec(secretValueBytes, cryptoAlgoName);
	}

	/**
	 * This method is used to concatenate input parameters into a single value separated by '-'
	 *
	 * @param leagueType This is a type of a league.
	 * @param playerId This is an ID of a player.
	 * @param style This is a style of image.
	 * @return a concatenated parameters separated by '-'.
	 *
	 * @throws IllegalArgumentException if any of the argument is not valid.
	 */
	private static String getInputData(final String leagueType, final String playerId, final String style)
			throws IllegalArgumentException {
		if (!isValidArgument(leagueType) || !isValidArgument(playerId) || !isValidArgument(style))
			throw new IllegalArgumentException("One of the input parameters is either not provided or it's blank");
		return leagueType + "-" + playerId + "-" + style;
	}

	/**
	 * This method is used to validate input argument based on either its not null and not have zero length.
	 *
	 * @param input It is an argument to be validated.
	 * @return <ul>
	 * 				<li>{@code true} in case the input parameter is valid</li>
	 * 				<li>{@code false} in case the input parameter is invalid</li>
	 * 			</ul>
	 */
	private static boolean isValidArgument(final String input) {
		return input != null && input.length() != 0;
	}

	/**
	 * This method is used prepare the final output depending on the leagueType, playerId and hexadecimal code.
	 * @param leagueType signifies type of league.
	 * @param style signifies style of image.
	 * @param hexadecimalHmac is hexadecimal value of Hashed Mac.
	 * @return a concatenated string of inputs parameters.
	 *
	 * @throws IllegalArgumentException if the base URL is not valid.
	 */
	private static String getFinalPlayerUrl(final String leagueType, final String style,final String hexadecimalHmac) {
		if(!isValidArgument(CloudFrontBase)) throw new IllegalArgumentException("Base URL is either not provided or it's blank");
		return CloudFrontBase + "/" + leagueType + "/" + style + "/" + hexadecimalHmac + "." + "png";
	}

	/**
	 * This method is used to get Hexadecimal value for an array of byte
	 * @param inPutBytes this is the input array of bytes whose hexadecimal value to return
	 * @return a Hexadecimal value of input array of bytes.
	 */
	private static String getHexadecimalEncode(byte[] inPutBytes) {
		StringBuilder result = new StringBuilder();
		for (byte singleByte : inPutBytes) {
			result.append(String.format("%02x", singleByte));
		}
		return result.toString();
	}

	public static void main(String args[]) {
		try {
			System.out.println(PlayerImageUrl.getPlayerUrl("nfl", "145267", "original"));
			System.out.println(PlayerImageUrl.getPlayerUrl("nfl", "851231", "original"));
			System.out.println(PlayerImageUrl.getPlayerUrl("nfl", "145267", "thumb"));
			System.out.println(PlayerImageUrl.getPlayerUrl("nfl", "851231", "thumb"));
			System.out.println(PlayerImageUrl.getPlayerUrl("mlb", "232210", "original"));
			System.out.println(PlayerImageUrl.getPlayerUrl("mlb", "601231", "original"));
			System.out.println(PlayerImageUrl.getPlayerUrl("mlb", "232210", "thumb"));
			System.out.println(PlayerImageUrl.getPlayerUrl("mlb", "601231", "thumb"));
		} catch (GeneralSecurityException e) {
			System.out.println("Error");
			System.out.println(e.getMessage());
		}
	}
}
