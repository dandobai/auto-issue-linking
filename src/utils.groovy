import com.atlassian.jira.component.ComponentAccessor

class JiraUtils {
    static def getIssueByKey(String issueKey) {
        def issueManager = ComponentAccessor.issueManager
        return issueManager.getIssueObject(issueKey)
    }

    static def createLink(Issue epic, Issue theme) {
        def issueLinkManager = ComponentAccessor.issueLinkManager
        issueLinkManager.createIssueLink(epic.id, theme.id, issueLinkManager.getIssueLinkType(10000), null, ComponentAccessor.jiraAuthenticationContext.loggedInUser)
    }

    static def removeLink(Issue epic, Issue theme) {
        def issueLinkManager = ComponentAccessor.issueLinkManager
        def links = issueLinkManager.getIssueLinks(epic.id).findAll { it.destinationObject == theme }
        links.each { issueLinkManager.removeIssueLink(it) }
    }
}
