# Blastoise
This is an assignment made for the course TTTK2023 Object Oriented Software Engineering

## Explanation
Now there's a timeline, where each tick (1/30 seconds), the system will run through the queue and check if anything's need to be updated, terminated according to current time(LocalDateTime.now()).
The 'add client' button will emulate a phone from user's perspective, they can add themselves to a queue and make a payment (just an alert, or message box, or a modal). Then it will reflect on the dashboard.

## TODO
1. Dynamically add component (machine id and status + listview) for each machine in the dashboard
2. Using ObservableList, link the date in machine queue to listview
3. Add a callback to observable list (inside constructor), to trigger an update when QueueItem Status changed
4. In the updateItem callback for list cell, make the item change background color and text color (if necessary) for different status of queueitem
5. Add button to call for different machines inside client phone
6. (optional) allow the user to set time they want to queue
    - assginment algorithm in machine types need to be changed
    - add a datetime picker in client
7. Add a screen to client to view machine status and lock/unlock it.
8. Fix client Auth screen ratio size issue

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

