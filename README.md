# Theme-Epic Link Synchronization for Jira Data Center

This repository hosts a solution for synchronizing relationships between two Jira issue types: **Theme** and **Epic**. In our setup, an Epic contains an Elements Connect custom field ("Theme Selection") that allows the user to select a Theme issue. When a Theme is selected, the system automatically creates a link between the Epic and the Theme – and vice versa – ensuring that the hierarchical relationship is always maintained (e.g., for visualization in Structure).

The solution is implemented in **Groovy** using ScriptRunner (or an alternative custom Jira plugin approach) within a Jira Data Center environment.

## Table of Contents

- [Overview](#overview)
- [Repository Structure](#repository-structure)
- [Requirements](#requirements)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [Customization](#customization)
- [License](#license)
- [Contributing](#contributing)

## Overview

This project addresses the following key problems:
- **Bidirectional Synchronization:**  
  - If the custom field ("Theme Selection") within an Epic is updated, an issue link between the Epic and the selected Theme is automatically created.
  - If a user manually creates or removes the link between an Epic and a Theme, the custom field is synchronized to reflect the current state.
- **Deletion Handling:**  
  - When a link is deleted, the custom field value is also cleared in the Epic.
- **Cross-Project Support:**  
  - The solution supports cases where Themes and Epics may reside in different projects.
- **Relationship Model:**  
  - One Theme may be linked to multiple Epics.

## Repository Structure

```plaintext
repo-root/
├── docs/
│   ├── requirements.md   # Detailed project requirements and functionality overview.
│   ├── setup.md          # Step-by-step installation and configuration instructions.
│   └── usage.md          # Usage guide and troubleshooting tips.
├── src/
│   ├── link_sync.groovy      # Core synchronization script between Themes and Epics.
│   ├── event_listener.groovy # Jira event listener triggering the synchronization.
│   ├── utils.groovy          # Utility functions for common Jira operations.
│   └── config.yaml           # Configuration file (e.g., Jira instance URL, API token, custom field names).
├── README.md             # This file – an overview and guide for the project.
├── requirements.txt      # Dependency list (if needed).
└── setup.sh              # Optional shell script for automating initial setup tasks.

# Requirements

For a full list of functional and technical requirements, please refer to the [docs/requirements.md](docs/requirements.md) file.

# Installation and Setup

## Clone this repository:
```bash
git clone https://github.com/your_username/your_repository.git

# Prepare your Jira Environment

- Ensure you have an Atlassian Data Center Jira instance installed.
- Install ScriptRunner for Jira (or configure your custom plugin environment) to allow Groovy script execution.

## Configure ScriptRunner

- Navigate to the ScriptRunner section in your Jira administration panel.
- Create a new Listener and paste the content from `src/event_listener.groovy`.

## Configuration File

- Update `src/config.yaml` with your Jira instance details, API token, and the custom field name (“Theme Selection”).

## Follow the Detailed Setup Instructions

- See [docs/setup.md](docs/setup.md) for a complete, step-by-step setup guide.

## Optional Automated Setup

- If applicable, you can run `setup.sh` to automate directory or other preparation tasks.

# Usage

The solution leverages three main scripts that work together:

- **event_listener.groovy**  
  Listens for issue updates (for example, when the custom field is changed or an issue link is modified). On detection, it triggers the synchronization process.
  
- **link_sync.groovy**  
  Checks if a Theme has been selected via the custom field and creates (or removes) the corresponding issue link between the Epic and Theme accordingly.
  
- **utils.groovy**  
  Provides helper methods for common operations such as issue retrieval and issue link creation/deletion.

For detailed usage instructions and testing scenarios, please refer to [docs/usage.md](docs/usage.md).

# Customization

Feel free to extend or modify the scripts:

- Adjust the naming of the custom field or the type of issue link in your `src/config.yaml`.
- Enhance the synchronization logic by editing `src/link_sync.groovy` and `src/event_listener.groovy`.
- Leverage the functions in `src/utils.groovy` for additional Jira operations if required.

# License

This project is provided under the MIT License.

# Contributing

Contributions are welcome. Please open an issue or submit a pull request to suggest improvements or report any bugs.

If you have any questions or encounter issues during setup or usage, please feel free to open an issue in this repository.