import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.component.ComponentAccessor

class JiraUtilsTest {

    static void testGetIssueByKey() {
        String testIssueKey = "TEST-123"
        def issueManager = ComponentAccessor.issueManager

        // Simulate fetching issue
        Issue issue = JiraUtils.getIssueByKey(testIssueKey)
        assert issue != null : "Issue should not be null"
        assert issue.key == testIssueKey : "Retrieved issue key should match the input key"

        println("testGetIssueByKey: Passed")
    }

    static void testCreateLink() {
        Issue epic = Mock(Issue) // Mocked epic issue
        Issue theme = Mock(Issue) // Mocked theme issue
        def issueLinkManager = ComponentAccessor.issueLinkManager

        // Simulate creation of a link
        try {
            JiraUtils.createLink(epic, theme)
            println("testCreateLink: Link created successfully")
        } catch (Exception e) {
            assert false : "Link creation should not throw an exception: ${e.message}"
        }
    }

    static void testRemoveLink() {
        Issue epic = Mock(Issue) // Mocked epic issue
        Issue theme = Mock(Issue) // Mocked theme issue
        def issueLinkManager = ComponentAccessor.issueLinkManager

        // Simulate removal of a link
        try {
            JiraUtils.removeLink(epic, theme)
            println("testRemoveLink: Link removed successfully")
        } catch (Exception e) {
            assert false : "Link removal should not throw an exception: ${e.message}"
        }
    }

    static void testThemeLinkedToMultipleEpics() {
        Issue theme = Mock(Issue) // Mock a theme issue
        List<Issue> epics = [Mock(Issue), Mock(Issue)] // Mock a list of epic issues
        def issueLinkManager = ComponentAccessor.issueLinkManager

        epics.each { epic ->
            try {
                JiraUtils.createLink(epic, theme) // Create links for each epic
                println("Link created: ${epic.key} -> ${theme.key}")
            } catch (Exception e) {
                assert false : "Link creation failed for ${epic.key}: ${e.message}"
            }
        }
        println("testThemeLinkedToMultipleEpics: Passed")
}

    static void testRemoveLinksForTheme() {
        Issue theme = Mock(Issue) // Mock a theme issue
        List<Issue> epics = [Mock(Issue), Mock(Issue)] // Mock a list of epic issues
        def issueLinkManager = ComponentAccessor.issueLinkManager

        // Simulate link removal
        epics.each { epic ->
            try {
                JiraUtils.removeLink(epic, theme) // Remove links for each epic
                println("Link removed: ${epic.key} -> ${theme.key}")
            } catch (Exception e) {
                assert false : "Failed to remove link for ${epic.key}: ${e.message}"
            }
        }
        println("testRemoveLinksForTheme: Passed")
    }

}


// Execute the tests
JiraUtilsTest.testGetIssueByKey()
JiraUtilsTest.testCreateLink()
JiraUtilsTest.testRemoveLink()
JiraUtilsTest.testThemeLinkedToMultipleEpics()
JiraUtilsTest.testRemoveLinksForTheme()
