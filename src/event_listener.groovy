import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.event.api.EventListener

class EventListener {

    @EventListener
    static void handleIssueUpdatedEvent(Issue issue) {
        def customFieldManager = ComponentAccessor.customFieldManager

        // Retrieve custom field
        CustomField themeField = customFieldManager.getCustomFieldObjects()
                .find { it.name == "Theme Selection" }

        if (themeField) {
            Issue selectedTheme = issue.getCustomFieldValue(themeField) as Issue

            if (selectedTheme) {
                log.info("Issue updated: ${issue.key}, Theme: ${selectedTheme.key}")
                LinkSync.createIssueLink(issue, selectedTheme)
            } else {
                LinkSync.clearThemeField(issue)
            }
        } else {
            log.warn("Theme Selection custom field not found.")
        }
    }
}