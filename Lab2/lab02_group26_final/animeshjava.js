var Namee1;
var Namee2;
function disp(){
	var Name1=prompt("Player 1 name:","");
	var Name2=prompt("Player 2 name:","");
	Namee1=Name1;
	Namee2=Name2;
	document.getElementById("play1").innerHTML= Name1;
	document.getElementById("play2").innerHTML= Name2;
}

var player=1;

function clickBtn(btn){
	
	if(player==1){
	document.getElementById(btn).value=" ";
	document.getElementById(btn).disabled="disabled";
	document.getElementById(btn).style.backgroundImage= "url('cross1.jpg')";
	document.getElementById(btn).style.backgroundImage.width="100px";
	document.getElementById(btn).style.backgroundImage.height="100px";
			player=2;winner();}
	else
		if(player==2){
						document.getElementById(btn).value="  ";
						document.getElementById(btn).disabled="disabled";
						document.getElementById(btn).style.backgroundImage= "url('circle2.jpg')";
						document.getElementById(btn).style.backgroundImage.width="100px";
						document.getElementById(btn).style.backgroundImage.height="100px";
						player=1;winner();
					}
}

function winner(){
	if((document.getElementById("btn1").value==" " && document.getElementById("btn2").value==" " && document.getElementById("btn3").value==" ")||(document.getElementById("btn1").value==" " && document.getElementById("btn5").value==" " && document.getElementById("btn9").value==" ")||(document.getElementById("btn1").value==" " && document.getElementById("btn4").value==" " && document.getElementById("btn7").value==" ")||(document.getElementById("btn4").value==" " && document.getElementById("btn5").value==" " && document.getElementById("btn6").value==" ")||(document.getElementById("btn7").value==" " && document.getElementById("btn8").value==" " && document.getElementById("btn9").value==" ")||(document.getElementById("btn7").value==" " && document.getElementById("btn5").value==" " && document.getElementById("btn3").value==" ")||(document.getElementById("btn2").value==" " && document.getElementById("btn5").value==" " && document.getElementById("btn8").value==" ")||(document.getElementById("btn3").value==" " && document.getElementById("btn6").value==" " && document.getElementById("btn9").value==" ")){alert("The winner is "+ Namee1); reset();}
	else
		if((document.getElementById("btn1").value=="  " && document.getElementById("btn2").value=="  " && document.getElementById("btn3").value=="  ")||(document.getElementById("btn1").value=="  " && document.getElementById("btn5").value=="  " && document.getElementById("btn9").value=="  ")||(document.getElementById("btn1").value=="  " && document.getElementById("btn4").value=="  " && document.getElementById("btn7").value=="  ")||(document.getElementById("btn4").value=="  " && document.getElementById("btn5").value=="  " && document.getElementById("btn6").value=="  ")||(document.getElementById("btn7").value=="  " && document.getElementById("btn8").value=="  " && document.getElementById("btn9").value=="  ")||(document.getElementById("btn7").value=="  " && document.getElementById("btn5").value=="  " && document.getElementById("btn3").value=="  ")||(document.getElementById("btn2").value=="  " && document.getElementById("btn5").value=="  " && document.getElementById("btn8").value=="  ")||(document.getElementById("btn3").value=="  " && document.getElementById("btn6").value=="  " && document.getElementById("btn9").value=="  ")){alert("The winner is "+ Namee2); reset();}

}
function reset(){
	
	window.location.reload();
}
function toggle(){
		var yoho=document.getElementById("dub");
		if(yoho.style.display!=='none'){yoho.style.display='none';}
		else{yoho.style.display='block';}
	}

		