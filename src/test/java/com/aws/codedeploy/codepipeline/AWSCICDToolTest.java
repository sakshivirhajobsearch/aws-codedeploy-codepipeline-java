package com.aws.codedeploy.codepipeline;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import software.amazon.awssdk.services.codedeploy.CodeDeployClient;
import software.amazon.awssdk.services.codedeploy.model.ListApplicationsResponse;
import software.amazon.awssdk.services.codepipeline.CodePipelineClient;
import software.amazon.awssdk.services.codepipeline.model.ListPipelinesResponse;
import software.amazon.awssdk.services.codepipeline.model.PipelineSummary;

public class AWSCICDToolTest {

	private CodeDeployClient mockCodeDeployClient;
	private CodePipelineClient mockCodePipelineClient;

	@BeforeEach
	public void setup() {
		mockCodeDeployClient = mock(CodeDeployClient.class);
		mockCodePipelineClient = mock(CodePipelineClient.class);
	}

	@Test
	public void testListCodeDeployApplications() {
		ListApplicationsResponse response = ListApplicationsResponse.builder().applications("App1", "App2").build();

		when(mockCodeDeployClient.listApplications()).thenReturn(response);

		ListApplicationsResponse actual = mockCodeDeployClient.listApplications();
		assertNotNull(actual);
		assertEquals(2, actual.applications().size());
		assertTrue(actual.applications().contains("App1"));
	}

	@Test
	public void testListCodePipelinePipelines() {
		PipelineSummary pipeline = PipelineSummary.builder().name("Pipeline1").created(Instant.now()).build();

		ListPipelinesResponse response = ListPipelinesResponse.builder().pipelines(pipeline).build();

		when(mockCodePipelineClient.listPipelines()).thenReturn(response);

		ListPipelinesResponse actual = mockCodePipelineClient.listPipelines();
		assertNotNull(actual);
		assertEquals(1, actual.pipelines().size());
		assertEquals("Pipeline1", actual.pipelines().get(0).name());
	}
}
