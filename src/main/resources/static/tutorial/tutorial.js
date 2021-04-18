//tutorial modal created
console.log("Tutorail");
var tutorialModal = document.createElement("div");
tutorialModal.id = "tutorial";
tutorialModal.className = "modal hide";

//header of modal created
var modalHeader = document.createElement("div");
modalHeader.className = "modal-header";

//close button of modal created in header
var closeButton = document.createElement("button");
closeButton.setAttribute("data-dismiss", "modal");
closeButton.className = "close";
closeButton.type = "button";
closeButton.appendChild(document.createTextNode("skip>>"));

//close button is attached in header
modalHeader.appendChild(closeButton);

//modal heading is created
var modalHeading = document.createElement("h3");
modalHeading.appendChild(document.createTextNode("Tutorial"));

//modal heading is atteched in modal header
modalHeader.appendChild(modalHeading);

//header of modal is attached with main modal
tutorialModal.appendChild(modalHeader);

//modal body is created
var modalBody = document.createElement("div");
modalBody.className = "modal-body";

//body title is created in this will show the page of body
var bodyTitle = document.createElement("div");
bodyTitle.className = "widget-title";

//pageTab created
var pageTab = document.createElement("ul");
pageTab.className = "nav nav-tabs";
//pageTab.setAttribute("data-toggle", "tab");

var pageTabList = document.createElement("li");
pageTabList.className = "active";

var pageTabListContent = document.createElement("a");
pageTabListContent.id = "pageNumberText";
pageTabListContent.innerHTML = "1/15";
pageTabListContent.href = "javascript:void(0)";

var input = document.createElement("input");
input.id = "pageNumberValue";
input.type = "hidden";
input.value = 0;

pageTabList.appendChild(input);

pageTabList.appendChild(pageTabListContent);

pageTab.appendChild(pageTabList);

//pageTab appended in body title
bodyTitle.appendChild(pageTab);

//body title is attached in modal body
modalBody.appendChild(bodyTitle);

var modalContent = document.createElement("div");
modalContent.className = "widget-content tab-content";

var tab = document.createElement("div");
tab.id = "tab1";

var center = document.createElement("center");

var mainHeader = document.createElement("h3");
mainHeader.id = "mainHeader";
mainHeader.appendChild(document.createTextNode("Welcome to Student Attendance Management Application!"));

center.appendChild(mainHeader);

var secondHeader = document.createElement("h6");
secondHeader.id = "secondHeader";
secondHeader.appendChild(
  document.createTextNode(
    "This short tutorial will walk you through all of the features of this application."
  )
);

center.appendChild(secondHeader);

tab.appendChild(center);

var center = document.createElement("center");

var tabParagraph = document.createElement("p");
tabParagraph.id = "paraContent";
tabParagraph.innerHTML =
  'If you want to dive right in, feel free to click the "Skip" button above. Otherwise, press "Next"!';

center.appendChild(tabParagraph);

tab.appendChild(center);

var center = document.createElement("center");

var tabImg = document.createElement("img");
tabImg.id = "paraImg";
tabImg.src = "img/logo.png";
tabImg.title = "tutorialImg";

center.appendChild(tabImg);

tab.appendChild(center);

modalContent.appendChild(tab);

modalBody.appendChild(modalContent);

//modal body is attached in modal
tutorialModal.appendChild(modalBody);

//modal footer is created
var modalFooter = document.createElement("div");
modalFooter.className = "modal-footer";

var previous = document.createElement("a");
previous.id = "previous";
previous.className = "btn btn-primary";
//previous.setAttribute("data-toggle", "tab");
previous.href = "javascript:void(0);";
//previous.href = "#tab1";
previous.appendChild(document.createTextNode("previous"));

modalFooter.appendChild(previous);

var next = document.createElement("a");
next.href = "javascript:void(0);";
next.id = "next";
next.className = "btn btn-primary";
//next.setAttribute("data-toggle", "tab");
//next.href = "#tab1";
next.appendChild(document.createTextNode("next"));

modalFooter.appendChild(next);

//modal footer is attached
tutorialModal.appendChild(modalFooter);

document.getElementById("content").appendChild(tutorialModal);

tutorialData = [
  {
    pageNumber: 0,
    mainHeader: "Welcome to Student Attendance Management Application!",
    secondHeader:
      "This short tutorial will walk you through all of the features of this application.",
    paraContent:
      'If you want to dive right in, feel free to click the "Skip" button above. Otherwise, press "Next"!',
    paraImg: "img/logo.png",
  },
  {
    pageNumber: 1,
    mainHeader: "What features we have?",
    secondHeader: "Sidebar Menu",
    paraContent:
      "This application works on the user data all the links in the sidebar menu will lead you to multiple interfaces were user have to insert the data by filling various forms",
    paraImg: "img/tutorial/sidebar-menu.png",
  },
  {
    pageNumber: 2,
    mainHeader: "Add NFC Tags",
    secondHeader: "NFC Tag Information",
    paraContent:
      "Enter NFC Tag values into your system these Tag are to identify unique Student and Faculty",
    paraImg: "img/tutorial/rfid-form.png",
  },
  {
    pageNumber: 3,
    mainHeader: "Add Class Room's",
    secondHeader: "Class Room Information",
    paraContent:
      "Add Class room where lecture will be taken and device code that you have attached outside of Class Room",
    paraImg: "img/tutorial/class-form.png",
  },
  {
    pageNumber: 4,
    mainHeader: "Add Subject",
    secondHeader: "Provide Subjects of your department",
    paraContent:
      "Provide subjects for which you want to maintain attendance",
    paraImg: "img/tutorial/subject-form.png",
  },
  {
    pageNumber: 5,
    mainHeader: "Add Faculty",
    secondHeader: "Provide Faculties Information",
    paraContent:
      "Along with faculty details you also have attach a NFC tag, also provide username and password for faculty using which they can login to system and mark attendance",
    paraImg: "img/tutorial/faculty-form.png",
  },
  {
    pageNumber: 6,
    mainHeader: "Add Students",
    secondHeader: "Provide Students Information",
    paraContent:
      "Along with student details you also have attach a NFC tag to a student, so identify system can maintain his/her attendance",
    paraImg: "img/tutorial/student-form.png",
  },
  {
    pageNumber: 7,
    mainHeader: "Add Guardian Infomation",
    secondHeader:
      "Provide Parent/Guardian Contact Information",
    paraContent:
      "Double check Guardian Information with this information system will send alert, if information is not valid alert won't work",
    paraImg: "img/tutorial/guardian-info.png",
  },
  {
    pageNumber: 8,
    mainHeader: "Mark Attendance",
    secondHeader: "Mark attendance by Faculty",
    paraContent:
      "Once Students are loggedIn Faculty can click on mark attendance button system will save students attendance",
    paraImg: "img/tutorial/mark-attendance.png",
  },
  {
    pageNumber: 9,
    mainHeader: "Recent 7 days attendance",
    secondHeader:
      "View previous 7 days Student statistics",
    paraContent:
      "Admin can view count of present students of recent 7 days",
    paraImg: "img/tutorial/recent-7-days.png",
  },
  {
    pageNumber: 10,
    mainHeader: "Student Monthly Report",
    secondHeader:
      "View Students monthly report statistics",
    paraContent:
      "Admin can view average attendance of  students",
    paraImg: "img/tutorial/student-month-report.png",
  },
  {
    pageNumber: 11,
    mainHeader: "Subject details Attendance of Student",
    secondHeader:
      "View Students details attendance per subject",
    paraContent:
      "Admin can view Students details attendance per subject",
    paraImg: "img/tutorial/subject-details.png",
  },
  {
    pageNumber: 12,
    mainHeader: "Send Attendance Details To Guardian",
    secondHeader:
      "Send Alert & Average Attendance to Guardians",
    paraContent:
      "Admin can view Students Overall/Monthly average attendance of Student also admin can send Alert/Info mail to Guardian About their children attendance",
    paraImg: "img/tutorial/Alert-Mail.png",
  },
  {
    pageNumber: 13,
    mainHeader: "Subject per Day Attendance",
    secondHeader:
      "View Subjects per day attendance",
    paraContent:
     "Admin can view Subjects per day attendance by selecting proper values",
    paraImg: "img/tutorial/Perday-subject-attendance.png",
  },
  {
    pageNumber: 14,
    mainHeader: ":) Enjoy :) ",
    secondHeader:
      "I hope this tool will help you to Manage & Maintain Students Attendance! ",
    paraContent:
      "",
    paraImg: "img/tutorial/thumb.jpg",
  },
];
console.log(tutorialData);
console.log("tutorialData.length", tutorialData.length);

document.getElementById("next").onclick = function () {
  var pageNumberValue = parseInt(
    document.getElementById("pageNumberValue").value
  );
  var mainHeader = document.getElementById("mainHeader");
  var secondHeader = document.getElementById("secondHeader");
  var pageNumberText = document.getElementById("pageNumberText");
  var paraContent = document.getElementById("paraContent");
  var paraImg = document.getElementById("paraImg");

  if (pageNumberValue >= tutorialData.length - 1) return;

  var content = tutorialData[pageNumberValue + 1];

  console.log(content);
  mainHeader.innerHTML = content.mainHeader;
  secondHeader.innerHTML = content.secondHeader;
  pageNumberText.innerHTML = content.pageNumber + 1 + "/" + tutorialData.length;
  paraContent.innerHTML = content.paraContent;
  paraImg.src = content.paraImg;
  document.getElementById("pageNumberValue").value = content.pageNumber;
};

///previous page
document.getElementById("previous").onclick = function () {
  console.log("hello");
  var pageNumberValue = parseInt(
    document.getElementById("pageNumberValue").value
  );
  var mainHeader = document.getElementById("mainHeader");
  var secondHeader = document.getElementById("secondHeader");
  var pageNumberText = document.getElementById("pageNumberText");
  var paraContent = document.getElementById("paraContent");
  var paraImg = document.getElementById("paraImg");

  console.log(pageNumberValue);

  if (pageNumberValue <= 0) return;

  var content = tutorialData[pageNumberValue - 1];

  mainHeader.innerHTML = content.mainHeader;
  secondHeader.innerHTML = content.secondHeader;
  pageNumberText.innerHTML = content.pageNumber + 1 + "/" + tutorialData.length;
  paraContent.innerHTML = content.paraContent;
  paraImg.src = content.paraImg;
  document.getElementById("pageNumberValue").value = content.pageNumber;
};
