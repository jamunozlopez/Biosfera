/* author: Michael Rottmeier */
/* Creation date: 22.12.2001 */

/*********************************************************************************/
/*																				 */
/*  This script may NOT be used in any way without the permission of the author  */
/*																				 */
/*********************************************************************************/


function CreateLetterArray(i,WordNum){
var Output = '';

	for (var i=0; i<Letters.length; i++){
		if (UsedLetters[i] != Letters.charAt(i)){
			Output += ' <font size="+2"><b><a href="javascript:parent.CheckLetter(\'' + WordNum + '\',' + i + ')" ';
			Output += 'onMouseOver="window.status=\'Choose the letter ' + Letters.charAt(i) + '\';return true" ';
			Output += 'onMouseOut="window.status=\'\';return true">';
			Output += Letters.charAt(i) + '</a></b></font> ';
			}
			else{
				Output += ' <font size="+2" color="gray"><b>' + Letters.charAt(i) + '</b></font> ';
				}
		if (i == (Math.round(Letters.length/2)-1)){
			Output += '<br>';
			}
		}
		
		return Output;
}
