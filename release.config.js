module.exports = {
    branches: ["main"], // Specify your release branch
    plugins: [
        "@semantic-release/commit-analyzer",
        "@semantic-release/release-notes-generator",
        "@semantic-release/changelog",
        "@semantic-release/github",
        "@semantic-release/git"
    ]
};