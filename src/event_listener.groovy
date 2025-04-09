import com.atlassian.jira.event.type.EventTypeManager
import com.atlassian.jira.issue.IssueEvent
import com.atlassian.jira.component.ComponentAccessor

def eventTypeManager = ComponentAccessor.getComponent(EventTypeManager)
def issueManager = ComponentAccessor.issueManager

def eventType = eventTypeManager.getEventType(event.eventTypeId)

if (eventType.name == "Issue Updated") {
    def issue = event.issue
    def customFieldManager = ComponentAccessor.customFieldManager
    def themeField = customFieldManager.getCustomFieldObjectByName("Theme Selection")
    def selectedTheme = issue.getCustomFieldValue(themeField) as Issue
    
    if (selectedTheme) {
        log.info("Issue módosítva: ${issue.key} - Theme: ${selectedTheme.key}")
        // Meghívja a link_sync.groovy scriptet
        new File("src/link_sync.groovy").execute()
    }
}
