import com.cloudbees.plugins.credentials.impl.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.domains.*
import org.jenkinsci.plugins.plaincredentials.*
import org.jenkinsci.plugins.plaincredentials.impl.*
import hudson.util.Secret

def env = System.getenv()
def store = SystemCredentialsProvider.getInstance().getStore()

// Token for initial GitHub organization
def credentialID = 'global-git'
def gitUser = env['GIT_USER']
def gitToken = env['GIT_TOKEN']

if (gitUser && gitToken) {
  println "-> creating common creds for ci/cd user"
  def gitCredential =  new UsernamePasswordCredentialsImpl(
    CredentialsScope.GLOBAL,
    credentialID,
    'Common github credentials',
    gitUser,
    gitToken
  )

  // Some plugins use the token via secret text, so provide that too
  println "--> creating secret text credentials for ci/cd user"
  def gitCredentialText = new StringCredentialsImpl(
    CredentialsScope.GLOBAL,
    "${credentialID}-text",
    'Common github credential',
    Secret.fromString(gitToken)
  )

  // Add all credentials to Jenkins
  store.addCredentials(Domain.global(), gitCredential)
  store.addCredentials(Domain.global(), gitCredentialText)

} else {
  println 'DEBUG: Required environment variables GIT_USER and GIT_TOKEN required.'
}
