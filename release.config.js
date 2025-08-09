module.exports = {
    "name": "soen-6011-final-project",
    "version": "1.0.0",
    "devDependencies": {
        "@semantic-release/changelog": "^6.0.3",
        "@semantic-release/git": "^10.0.1",
        "@semantic-release/github": "^9.2.6",
        "semantic-release": "^22.0.12"
    },
    "release": {
        "branches": ["main"],
        "plugins": [
            "@semantic-release/commit-analyzer",
            "@semantic-release/release-notes-generator",
            "@semantic-release/changelog",
            "@semantic-release/github",
            "@semantic-release/git"
        ]
    }
};