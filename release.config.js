module.exports = {
    "name": "soen-6011-final-project",
    "version": "0.0.0-development",
    "description": "SOEN 6011 Final Project",
    "repository": {
        "type": "git",
        "url": "https://github.com/kapildev333/SOEN_6011_final_project.git"
    },
    "devDependencies": {
        "@semantic-release/changelog": "^6.0.3",
        "@semantic-release/git": "^10.0.1",
        "@semantic-release/github": "^9.2.6",
        "semantic-release": "^22.0.12"
    },
    "release": {
        "branches": ["main","master"],
        "plugins": [
            "@semantic-release/commit-analyzer",
            "@semantic-release/release-notes-generator",
            "@semantic-release/changelog",
            "@semantic-release/github",
            "@semantic-release/git"
        ]
    }
};