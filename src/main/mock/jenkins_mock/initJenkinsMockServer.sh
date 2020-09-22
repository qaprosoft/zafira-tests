JENKINS_MOCK_HOST=$1
curl -XGET $JENKINS_MOCK_HOST/job/create?name=testJob
curl -XPOST $JENKINS_MOCK_HOST/job/testJob/buildWithParameters/api/json?debug=false\&rerun_failures=false\&thread_count=10
