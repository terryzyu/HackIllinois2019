
# NurseConnect

Hello HackIllinois 2019! We are NurseConnect, a tool used to help nurses fill out questionnaires and field tests using just their voice. This way they can assist patients when performing these tests without worrying about how to record the results or interacting with their device.

# Tech
NurseConnect uses several open source projects to perform correctly:
* [Sphinx4](https://cmusphinx.github.io/) - A voice recognition tool developed in Java
* [Sphinx Knowledge Base Tool](http://www.speech.cs.cmu.edu/tools/lmtool-new.html) - Builds lexical and Language Models (LM) to use with Sphinx4
* [SimMetrics](https://github.com/Simmetrics/simmetrics) - Determines how similar two sentences are and more!

# Problems we ran into
Sphinx's provided dictionary wasn't accurate enough for our needs. It kept picking up surrounding noise and interpreting that. Therefore we had to create our own custom dictionaries and LMs. We didn't have any sample or test data to derive possible answers so we had to come up with our own to feed into Sphinx.

# Notes
Not everyone on the team was familiar with git so we decided to use it like a file sharing/hosting place. Each team member's work should be in their folder provided they uploaded/pushed it. 