import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.fields.CustomField

class EventListener {

    static void handleIssueUpdatedEvent(Issue issue) {
        def customFieldManager = ComponentAccessor.customFieldManager

        // Retrieve all custom fields and find the one with the desired name
        CustomField themeField = customFieldManager.getCustomFieldObjects()
            .find { it.name == "Theme Selection" }

        if (themeField) {
            Issue selectedTheme = issue.getCustomFieldValue(themeField) as Issue

            if (selectedTheme) {
                log.info("Issue updated: ${issue.key}, Theme: ${selectedTheme.key}")
                LinkSync.synchronizeLink(issue, selectedTheme)
            }
        }
    }
}

// Assuming this script is triggered upon "Issue Updated" event:
Issue issue = event.issue
EventListener.handleIssueUpdatedEvent(issue)
