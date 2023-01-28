# Coding Exercise

## Background
Your team has been asked to build a system to automatically update the "News Clock", as shown on the strap (lower thirds) on the BBC News Channel, highlighted in the picture attached, in blue.

Your task is to create an application to tell the Graphics System when the time has updated, and to update a simple UI overlay to show this change.
BBC News uses a Graphics Engine called [Caspar CG](https://casparcg.com). You can communicate with this Caspar CG Server using the [AMCP protocol](https://github.com/CasparCG/help/wiki/AMCP-Protocol#cg-invoke) over TCP/port 5250.
For the purpuse of this test, you are not expected to have a fully functioning Caspar CG system: a netcat listener will be enough.
Also, you can assume your are running the script on the same machine as the Caspar CG Server (localhost).

## Task 1: API
When the application is started, it should add a template called ```main/MAIN``` on to video channel ```1```. This would send something similar to:
```
CG 1 ADD 1 main/MAIN 1
```

Every minute, on the minute, the application should then increment the time on the UI, by sending an ```INVOKE``` command:
```
CG 1 INVOKE 1 "leftTab('on', 'BBC NEWS HH:MM')"
```
Where ```HH:MM``` is the current system time.

Remeber that each command should be terminated with both a carriage return and a linefeed character.


## Task 2: UI
CasparCG sends the payload of the INVOKE command to an HTML page, which will run the function passed in - in this case ```leftTab()```.
For the UI, you will need to create a simple HTML/JavaScript application, which will expose a function to the window, called ```leftTab```.

When called, this function should respond in one of two ways.
If called with the following parameters: ```leftTab('on', 'BBC NEWS HH:MM')```, it should:
* Animate in to the viewport, when the first parameter is 'on', and it is not already visible
* Show/update the text from the second parameter

Alternatively, if it is called with ```leftTab('off')```, it should:
* Animate out of the viewport.

The viewport is assumed to always be the standard HDTV resolution of 1920×1080, and the solution only needs to work in Chrome/Chromium.

## Notes
* You may implement your solution in any major language/framework. Please make sure you include instructions for us to run and test your code.
* If you are new to CasparCG, getting your head around it might take some time: don't worry, we have ve all been there. Let us know if you need extra time.
* You may not be able to finish both tasks, it's ok: send us what you have and please tell us what was easy and what was challenging.
* Your work will be evaluated against four key criteria:
  * Design, Modularisation and Componentisation
  * Testing Approach and Scenarios
  * Performance
  * Style and Readability

