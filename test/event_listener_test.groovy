import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.Issue
import org.junit.Test
import static org.junit.Assert.*

class EventListenerTest {

    @Test
    void testHandleIssueUpdatedEvent() {
        // Beállítás - Tesztadatok inicializálása
        Issue epic = JiraUtils.getIssueByKey("EPIC-123")

        def customFieldManager = ComponentAccessor.customFieldManager
        def themeField = customFieldManager.getCustomFieldObjectsByName("Theme Selection").find()
        Issue theme = JiraUtils.getIssueByKey("THEME-456")
        epic.setCustomFieldValue(themeField, theme)

        // Teszteljük az esemény kezelését
        EventListener.handleIssueUpdatedEvent(epic)

        def issueLinkManager = ComponentAccessor.issueLinkManager
        def links = issueLinkManager.getIssueLinks(epic.id).find { it.destinationObject == theme }

        // Ellenőrzés - A kapcsolat sikeresen létrejött
        assertNotNull("The link between Epic and Theme should exist after event update", links)
    }
}
