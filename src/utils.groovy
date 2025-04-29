import com.atlassian.jira.bc.issue.link.IssueLinkService
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
class JiraUtils {

    static Issue getIssueByKey(String issueKey) {
        return ComponentAccessor.issueManager.getIssueObject(issueKey)
    }

    static void removeIssueLink(Issue epic, Issue theme) {
        def issueLinkService = ComponentAccessor.getComponent(IssueLinkService)
        def user = ComponentAccessor.jiraAuthenticationContext.loggedInUser

        try {
            def links = ComponentAccessor.issueLinkManager.getIssueLinks(epic.id).findAll { it.destinationObject == theme }
            links.each { link ->
                def validationResult = issueLinkService.validateDeleteIssueLink(user, link.id)
                if (validationResult.isValid()) {
                    issueLinkService.deleteIssueLink(user, validationResult)
                    log.info("Link removed successfully: ${epic.key} -> ${theme.key}")
                } else {
                    log.warn("Failed to remove link: ${validationResult.errorCollection}")
                }
            }
        } catch (Exception e) {
            log.error("Error removing issue link: ${e.message}", e)
        }
    }
}