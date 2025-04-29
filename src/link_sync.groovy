import com.atlassian.extras.common.log.Logger
import com.atlassian.jira.bc.issue.link.IssueLinkService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.fields.CustomField
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.event.type.EventDispatchOption

class LinkSync {
    static final Logger log = Logger.getLogger(EventListener)

    static void createIssueLink(Issue epicIssue, Issue selectedTheme) {
        def issueLinkService = ComponentAccessor.getComponent(IssueLinkService)
        def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser

        try {
            IssueLinkService.IssueLinkValidationResult validationResult = issueLinkService.validateCreateIssueLink(
                    user,
                    epicIssue.id,
                    selectedTheme.id,
                    10000, // Link type ID
                    null
            )

            if (validationResult.isValid()) {
                issueLinkService.createIssueLink(user, validationResult)
                log.info("Link created successfully: ${epicIssue.key} -> ${selectedTheme.key}")
            } else {
                log.warn("Failed link validation: ${validationResult.errorCollection}")
            }
        } catch (Exception e) {
            log.error("Error creating issue link: ${e.message}", e)
        }
    }

    static void clearThemeField(Issue epicIssue) {
        def customFieldManager = ComponentAccessor.customFieldManager
        CustomField themeField = customFieldManager.getCustomFieldObjects().find { it.name == "Theme Selection" }

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