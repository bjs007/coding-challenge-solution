<H1>Java Player Image URLs</H1>
Java Player Image URLs is an application that generates unique URL for a given set of player Id, league type, and image style. 
<BR>
<H4>Prerequisites </H4>
Java

<H4>Implementation Overview </H4>

The method which actually composes the encoded URL is getPlayerUrl. This takes a set of input league type, player ID, and image style. It then uses the HmacSHA1 algorithm to find the encoded string for the set of input values. It also uses a secret value. It finally composes an URL in the required format given in the requirement.

<H4> Implementation Detail </H4>

<p>Here is the step for generation of unique URL: </p>
<p>
Get Input Data from User----> Concatenate them in a string separated by '-' after validation ----> Get Bytes for the string obtained from the previous step ----> Prepare secret key using HmacSHA1 algorithm ----> Initialize Mac Instance with secret key ----> Get the HMAC ----> Get the Hexadecimal code for HMAC ----> Prepare the final Encoded URL in the required format.
</p>
This section basically outlines the functionalities of the various function used while performing the task.

<h6>getPlayerUrl:</h6>

This method the main method which return unique URL with help of below helper methods.
<h6>getKeyInBytes:</h6>

This method returns an array of bytes for a String.
<h6>getSecretKeySpec:</h6>

This method returns a secret key depending on the algorithm specification.
<h6>getInputData:</h6>

This method is used to concatenate input stings into a single string separated by '-'.
<h6>isValidArgument:</h6>

This method is used to validate input argument based on either its not null and is not empty.
<h6>getHexadecimalEncode:</h6>

This method is used to getHexadecimal value for an array of byte.
<h6>getFinalPlayerUrl:</h6>

This method is used prepare the final output depending on the leagueType, image style and hexadecimal code.
<H4> To Run the Application</H4> 

It can be run on a terminal or an IDE such Eclipse.
<H4> Test Case</H4> 
List of expected encodes URLs have been provided in expected-results.txt. Additionally, a JUnit script can be written.
