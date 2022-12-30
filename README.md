# Blastoise
This is an assignment made for the course TTTK2023 Object Oriented Software Engineering

## TODO
1) research on css stylesheets for javafx
2) research on log4j (logger library)
3) make the main screen logo transparent background

## KY TODO:
1) We dont need a countDown timer implementation, just abuse the local date time function in java
2) Research on how to pop the item in the queue once the duration of the machine is reached
3) Dynamically adds queueItem to a UI list


## A queueItem will terminate when
1) Wash completed && idle for a period of time
OR
2) Wash completed  && User complete the wash session
OR
3) Wash is not completed but User ended it manually

### Concept
1) Users will first sign up with an account
2) Users then login into their account
3) Users select a machine type to queue for
4) A queue item is created and added into the queuelist consisting of
```
    a) User name email or smtg
    b) MachineType
    c) Time Stamp (start time + remaining time)
    d) QueueId (as in how many in the list)
```

### App Structure
1) FXML files
```
    a) main.fxml: The landing page of the whole application, contains logo,title,name, sign up, login interface
    b) signUp.fxml: Sign up interface
    c) login.fxml: login interface (maybe can share with signUp)
    d) queue.fxml: Users will select a machine to queue for
    e) waiting.fxml: Users will see their ET for using the machine
```
2) model files


### Tools used
1) JavaFx for UI
2) Java language

### Contributors
1) Cheok Kah Yeek
2) Chuo Ngiu Bing
3) Hwang Tian Ee
4) Lee Jia Yee

## Developer notes
1） Make sure you have maven installed on your device

### Installation
1) For now simply git clone this repo to your local storage


## Users Run and Installation guide
### Installation
1) For now simply git clone this repo to your local storage


### Run
1) Run ```mvn clean javafx:run``` for a fresh build

