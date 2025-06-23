module.exports = {
  branches: [
    'main',
    { name: 'release/*', channel: 'release', prerelease: false }
  ],
  repositoryUrl: 'https://github.com/GabrielPanta/VeterinariaApp.git',
  plugins: [
    '@semantic-release/commit-analyzer',
    '@semantic-release/release-notes-generator',
    '@semantic-release/github'
  ]
}

