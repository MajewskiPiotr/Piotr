var $ = AJS.$;
var baseURL = AJS.params.baseURL;
var jobKey;
var taskDate  = arguments[arguments.length - 1]


//var targetLanguages = ["LION-45", "LION-46", "LION-52", "LION-61", "LION-77", "LION-67", "LION-103", "LION-112", "LION-118", "LION-56", "LION-57"];
var targetLanguages = ["LION-77"];


var translationTask = {
    "fields": {
        "customfield_10500": [
            {"key": "LION-67"}
        ],
        "customfield_10501": [
            {"key": "LION-65"}
        ],
        "customfield_10102": 10.0,
        "customfield_10710": 0.0,
        "customfield_10711": 45.0,
        "customfield_10712": 0.0,

        "customfield_10715": "https://translate.google.com/ \r\n",
        "issuetype": {
            "name": "Translation Task",
        },
        "project": {
            "id": "10000",
        },
        // "customfield_10701": "89.0",
        "customfield_10702": 0.0,
        "customfield_10703": 0.0,
        "customfield_10705": 0.0,
        "customfield_10706": 3.0,
        "customfield_10707": 0.0,
        "customfield_10708": 0.0,
        "customfield_10709": 0.0,
        "customfield_10106": taskDate+"T10:22:00.000-0500",
        "customfield_10301": taskDate+"T10:22:00.000-0500",
        "customfield_12700": taskDate+"T10:22:00.000-0500",
        "summary": "Task JS CREATED",
        "customfield_10116": {
            "value": "No",
        },
        "duedate": taskDate
    }
}

var translationJob = {
        "fields": {
            "summary": "Automaty",
            "customfield_10004": "Automaty",
            "priority": {
                "id": "2"
            },
            "customfield_11301": {
                "name": "gpm2",
            },
            //"customfield_10313": "No",
            //"customfield_10314": "Mon Feb 20 00:00:00 EST 2017",
            "issuetype": {
                "name": "Translation Job",
            },
            "project": {
                "id": "10000",
            },
            "customfield_10311": [
                {"key": "LION-4"}
            ],
            "customfield_11401": 1,
            "customfield_10312": [
                {"key": "LION-14"}
            ],
            "customfield_10305": {
                "value": "No",
            },
            "customfield_10307": [
                {"key": "LION-249"}
            ],
            "customfield_10020": taskDate+"T18:00:00.000-0500",
            "customfield_10021": {
                "value": "No",
            },
            "customfield_10022": "45",
            "customfield_10023": "https://translate.google.com/",
            "customfield_10010": [
                {
                    "value": "GTT",
                }
            ],
            "customfield_10011": {
                "value": "TAT24",
            },
            "customfield_10012": {
                "value": "GTT",
            },
            "customfield_10015": " ",
            "customfield_10009": {
                "value": "UI",
            },
            "customfield_10115": {
                "value": "Other"
            },
        }
    }
;


function createJoBIssue(data, tasksAmount) {
    console.log("====================================");
    console.log("JOB CREATING ...");
    data.fields.customfield_11401 = tasksAmount
    $.ajax({
        url: baseURL + '/rest/api/2/issue',
        method: 'post',
        type: 'POST',
        async: false,
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (response) {
            jobKey = response.key
            console.log("JOB CREATED");
            console.log("JOB URL:", baseURL + "/browse/" + jobKey);
            console.log(response.key)
            console.log("====================================");
            console.log("TASKS CREATING ...");
            for (i = 0; i < tasksAmount; i++) {
                setTimeout(function () {
                    createTask(jobKey)
                }, i * 1000)
            }
        },
        error: function (error) {
            console.error(error);
        }
    })
    return jobKey;
}


function createTask(jobKey) {

    translationTask.fields.customfield_10002 = jobKey;

    var randomTargetLang = targetLanguages[Math.floor(Math.random() * targetLanguages.length)];
    translationTask.fields.customfield_10500 = [{"key": randomTargetLang}];

    $.ajax({
        url: baseURL + '/rest/api/2/issue',
        method: 'post',
        type: 'POST',
        async: false,
        data: JSON.stringify(translationTask),
        contentType: 'application/json',
        success: function (response) {
            console.log("TASK CREATED:", response.key);
            console.log(response)
        },
        error: function (error) {
            console.error(error);
        }
    })
}

//noinspection JSAnnotato,JSAnnotator
return createJoBIssue(translationJob, 5)




