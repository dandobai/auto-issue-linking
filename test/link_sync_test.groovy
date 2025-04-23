import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import org.junit.Test
import static org.junit.Assert.*

class LinkSyncTest {

    @Test
    void testCreateIssueLink() {
        // Beállítás - Tesztadatok inicializálása
        Issue epic = JiraUtils.getIssueByKey("EPIC-123")
        Issue theme = JiraUtils.getIssueByKey("THEME-456")

        // Teszteljük a kapcsolat létrehozását
        LinkSync.synchronizeLink(epic, theme)

        def issueLinkManager = ComponentAccessor.issueLinkManager
        def links = issueLinkManager.getIssueLinks(epic.id).find { it.destinationObject == theme }

        // Ellenőrzés - A kapcsolat sikeresen létrejött
        assertNotNull("The link between Epic and Theme should exist", links)
    }

    @Test
    void testClearThemeField() {
        // Beállítás - Tesztadatok inicializálása
        Issue epic = JiraUtils.getIssueByKey("EPIC-123")

        // Teszteljük a mező törlését
        LinkSync.clearThemeField(epic)

        def customFieldManager = ComponentAccessor.customFieldManager
        def themeField = customFieldManager.getCustomFieldObjectsByName("Theme Selection").find()
        def fieldValue = epic.getCustomFieldValue(themeField)

        // Ellenőrzés - A mező értéke null
        assertNull("Theme field should be cleared", fieldValue)
    }
}
