import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.link.IssueLinkManager

def issueManager = ComponentAccessor.issueManager
def issueLinkManager = ComponentAccessor.issueLinkManager
def customFieldManager = ComponentAccessor.customFieldManager

def epicIssue = issueManager.getIssueObject("EPIC-123") // Az Epic issue azonosítója
def themeField = customFieldManager.getCustomFieldObjectByName("Theme Selection")
def selectedTheme = epicIssue.getCustomFieldValue(themeField) as Issue

if (selectedTheme) {
    def existingLink = issueLinkManager.getIssueLinks(epicIssue.id).find { it.destinationObject == selectedTheme }

    if (!existingLink) {
        issueLinkManager.createIssueLink(epicIssue.id, selectedTheme.id, issueLinkManager.getIssueLinkType(10000), null, ComponentAccessor.jiraAuthenticationContext.loggedInUser)
        log.info("Kapcsolat létrehozva: ${epicIssue.key} -> ${selectedTheme.key}")
    }
}

// Ha a kapcsolat törlődik, frissítsük a mezőt is
def linkedThemes = issueLinkManager.getIssueLinks(epicIssue.id).findAll { it.issueLinkType.name == "Parent-Child" }
if (!linkedThemes) {
    epicIssue.setCustomFieldValue(themeField, null)
    issueManager.updateIssue(ComponentAccessor.jiraAuthenticationContext.loggedInUser, epicIssue, EventDispatchOption.DO_NOT_DISPATCH, false)
    log.info("Theme mező törölve az Epicben: ${epicIssue.key}")
}
