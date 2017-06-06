/**
 * Created by Piotr Majewski on 2017-06-05.
 */
function zmianaUsera(userID) {

    var userID
    var data = {
        FIELD_USER_ID: userID,
        FIELD_FUNCTION_ID: "",
        "canned-script": "com.onresolve.scriptrunner.canned.jira.admin.SwitchUser",
    }
    data.scriptParams = JSON.stringify(data)
    AJS.$.ajax({
        url: '/jira/rest/scriptrunner/latest/canned/com.onresolve.scriptrunner.canned.jira.admin.SwitchUser',
        type: 'post',
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        headers: {"X-Atlassian-token": "no-check"},
        success: function (resp) {
            console.log(resp)
        },
        data: data
    })

}
login = arguments[arguments.length - 1]
zmianaUsera(login);