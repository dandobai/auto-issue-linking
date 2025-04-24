import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.event.type.EventDispatchOption

class LinkSync {

    static void synchronizeLink(Issue epicIssue, Issue selectedTheme) {
        IssueLinkManager issueLinkManager = ComponentAccessor.issueLinkManager

        try {
            List existingLink = issueLinkManager.getIssueLinks(epicIssue.id).find { it.destinationObject == selectedTheme }

            if (!existingLink) {
                issueLinkManager.createIssueLink(
                    epicIssue.id,
                    selectedTheme.id,
                    issueLinkManager.getIssueLinkType(10000),
                    null,
                    ComponentAccessor.jiraAuthenticationContext.loggedInUser
                )
                log.info("Link created: ${epicIssue.key} -> ${selectedTheme.key}")
            }
        } catch (Exception e) {
            log.error("Failed to create issue link: ${e.message}", e)
        }
    }

    static void clearThemeField(Issue epicIssue) {
        def customFieldManager = ComponentAccessor.customFieldManager

        // Retrieve all custom fields and find the one with the desired name
        CustomField themeField = customFieldManager.getCustomFieldObjects()
            .find { it.name == "Theme Selection" }

        if (themeField) {
            try {
                epicIssue.setCustomFieldValue(themeField, null)
                ComponentAccessor.issueManager.updateIssue(
                    ComponentAccessor.jiraAuthenticationContext.loggedInUser,
                    epicIssue,
                    EventDispatchOption.DO_NOT_DISPATCH,
                    false
                )
                log.info("Theme field cleared for Epic: ${epicIssue.key}")
            } catch (Exception e) {
                log.error("Failed to clear Theme field: ${e.message}", e)
            }
        } else {
            log.warn("Theme field not found for Epic: ${epicIssue.key}")
        }
    }
}
