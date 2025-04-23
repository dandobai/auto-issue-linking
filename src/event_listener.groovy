import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.fields.CustomField

class EventListener {

    static void handleIssueUpdatedEvent(Issue issue) {
        def customFieldManager = ComponentAccessor.customFieldManager
        CustomField themeField = customFieldManager.getCustomFieldObjectsByName("Theme Selection").find()
        Issue selectedTheme = issue.getCustomFieldValue(themeField) as Issue

        if (selectedTheme) {
            log.info("Issue updated: ${issue.key}, Theme: ${selectedTheme.key}")
            LinkSync.synchronizeLink(issue, selectedTheme)
        }
    }
}

// Assuming this script is triggered upon "Issue Updated" event:
Issue issue = event.issue
EventListener.handleIssueUpdatedEvent(issue)
