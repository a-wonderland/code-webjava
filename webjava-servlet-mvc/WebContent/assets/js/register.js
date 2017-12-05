window.onload = function () {
	ErrCount = 0;
	btn = document.getElementById("register");
	// register event
	btn.addEventListener("click", validate);

	var inputs = document.getElementsByTagName("input");
	// validate pattern
	for (var i = 0; i < inputs.length; i++) {
		inputs[i].addEventListener("invalid", function (event) {
			// block default msg
			event.preventDefault();
			//event.target.setCustomValidity('');
			if (!event.target.validity.valid) {
				displayErroMSG(event.target, "Msg");
				ErrCount++;
			}
		});

		// "change" event can also use
		inputs[i].addEventListener("change", function (event) {
			// checkValidity() can also use
			// block default msg
			event.preventDefault();
			//event.target.setCustomValidity('');
			if (event.target.validity.valid) {
				displayMSG(event.target, "Msg");
			}
		});
	}
	/*// validate year
	selectYears = document.getElementById("selectYears");
	// validate month
	selectMonths = document.getElementById("selectMonths");
	// validate day
	selectDays = document.getElementById("selectDays");
	var age = document.getElementById("age");

	selectYears.addEventListener("change", function () {
		if (!isNaN(getAge())) {
			age.innerHTML = getAge() + " Years";
		}else {
			age.innerHTML = "";
		}

	});

	selectMonths.addEventListener("change", function () {
		if (!isNaN(getAge())) {
			age.innerHTML = getAge() + "Years";
		}else {
			age.innerHTML = "";
		}
	});

	selectDays.addEventListener("change", function () {
		if (!isNaN(getAge())) {
			age.innerHTML = getAge() + "Years";
		}else {
			age.innerHTML = "";
		}
	});*/

}

function getAge() {
	var yearVal = selectYears.options[selectYears.selectedIndex].value;
	var monthVal = selectMonths.options[selectMonths.selectedIndex].index;
	var dayVal = selectDays.options[selectDays.selectedIndex].value;
	//console.log(yearVal + " " + monthVal + " " + dayVal);

	if (yearVal && monthVal && dayVal) {
		var now = new Date();
		var birthDate = new Date(yearVal, monthVal, dayVal);
		// cal age
		var ageY = now.getFullYear() - birthDate.getFullYear();
		var ageM = now.getMonth() - birthDate.getMonth();
		if (ageM < 0 || (ageM === 0 && now.getDate() < birthDate.getDate())) {
			ageY--;
		}
		return ageY;
	}
}

function generateOptByValue(selectTag, value) {
	// create empty option
	var optNode = document.createElement("option");
	optNode.value = value;
	var text = document.createTextNode(value);
	// add text to option tag
	optNode.appendChild(text);
	// add option to select tag
	selectTag.appendChild(optNode);
}

function generateOpt1(selectTag, start, end, operator) {

	for (let i = start; operator < 0 ? i >= end : i <= end; operator < 0 ? i-- : i++) {
		// create <option>
		var optNode = document.createElement("option");
		var text = document.createTextNode(i);
		// add id
		optNode.value = i;
		// add text to option tag
		optNode.appendChild(text);
		// add option to select tag
		selectTag.appendChild(optNode);
	}
}

function generateOpt(selectTag, start, end, callback) {

	for (let i = start; i <= end; i++) {
		var value = callback();
		// create <option>
		var optNode = document.createElement("option");
		// generate text according to callback function
		var text = document.createTextNode(value);
		// add id
		optNode.value = value;
		// add text to option tag
		optNode.appendChild(text);
		// add option to select tag
		selectTag.appendChild(optNode);
	}
}


function validate() {
	//TODO
	//validateDateSelect();
	// toogle
	if (this.ErrCount === 0) {
		btn.className = "OK";
	} else {
		btn.className = "NG";
	}
}

// ■ common
function validateDateSelect() {

	// validate year
	var pYear = document.getElementById("year");
	// validate month
	var pMonth = document.getElementById("month");
	// validate day
	var pDay = document.getElementById("day");

	// get selected option
	// can also use selectYears.options[selectYears.selectedIndex].value

	var yearIndex = selectYears.options[selectYears.selectedIndex].index;
	var monthIndex = selectMonths.options[selectMonths.selectedIndex].index;
	var dayIndex = selectDays.options[selectDays.selectedIndex].index;

	if (yearIndex === 0) {
		this.ErrCount++;
		displayErroMSG(pYear, "Msg");
	} else {
		displayMSG(pYear, "Msg");
	}

	if (monthIndex === 0) {
		this.ErrCount++;
		displayErroMSG(pMonth, "Msg");
	} else {
		displayMSG(pMonth, "Msg");
	}

	if (dayIndex === 0) {
		this.ErrCount++;
		clearStyle(pDay, "DigitMsg");
		displayErroMSG(pDay, "Msg");
	} else if (dayIndex !== 0 && monthIndex !== 0) { // check days digit
		// 0 index is text
		if (selectDays.options[selectDays.selectedIndex].value <= monthDays[monthIndex - 1]) {
			//console.log(selectDays.options[selectDays.selectedIndex].value + " " + monthDays[monthIndex - 1]);
			clearStyle(pDay, "Msg");
			displayMSG(pDay, "DigitMsg");
		} else {
			//console.log("Error " + selectDays.options[selectDays.selectedIndex].value + " " + monthDays[monthIndex - 1]);
			clearStyle(pDay, "Msg");
			displayErroMSG(pDay, "DigitMsg");
		}
	} else {
		clearStyle(pDay, "DigitMsg");
		displayMSG(pDay, "Msg");
	}

}

function displayErroMSG(target, sufix) {
	target.className = "invalid";
	var msgObj = document.getElementById(target.id + sufix);
	msgObj.className = "textNG";
	var key = msgObj.id;
	msgObj.innerHTML = messages[key];
}

function displayMSG(target, sufix) {
	target.className = "";
	var msgObj = document.getElementById(target.id + sufix);
	msgObj.className = "textOK";
	msgObj.innerHTML = messages.okMsg;
}

function clearStyle(target, sufix) {
	target.className = "";
	var msgObj = document.getElementById(target.id + sufix);
	msgObj.className = "";
	msgObj.innerHTML = "";
}