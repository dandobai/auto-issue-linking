import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.link.IssueLinkManager

class JiraUtils {

    static Issue getIssueByKey(String issueKey) {
        def issueManager = ComponentAccessor.issueManager
        return issueManager.getIssueObject(issueKey)
    }

    static void createLink(Issue epic, Issue theme) {
        IssueLinkManager issueLinkManager = ComponentAccessor.issueLinkManager
        try {
            issueLinkManager.createIssueLink(
                epic.id,
                theme.id,
                issueLinkManager.getIssueLinkType(10000),
                null,
                ComponentAccessor.jiraAuthenticationContext.loggedInUser
            )
            log.info("Link created successfully: ${epic.key} -> ${theme.key}")
        } catch (Exception e) {
            log.error("Failed to create link: ${e.message}", e)
        }
    }

    static void removeLink(Issue epic, Issue theme) {
        IssueLinkManager issueLinkManager = ComponentAccessor.issueLinkManager
        try {
            def links = issueLinkManager.getIssueLinks(epic.id).findAll { it.destinationObject == theme }
            links.each { issueLinkManager.removeIssueLink(it) }
            log.info("Link removed successfully: ${epic.key} -> ${theme.key}")
        } catch (Exception e) {
            log.error("Failed to remove link: ${e.message}", e)
        }
    }
}
