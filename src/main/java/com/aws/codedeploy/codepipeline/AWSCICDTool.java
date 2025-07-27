package com.aws.codedeploy.codepipeline;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.codedeploy.CodeDeployClient;
import software.amazon.awssdk.services.codedeploy.model.ListApplicationsRequest;
import software.amazon.awssdk.services.codedeploy.model.ListApplicationsResponse;

import software.amazon.awssdk.services.codepipeline.CodePipelineClient;
import software.amazon.awssdk.services.codepipeline.model.ListPipelinesRequest;
import software.amazon.awssdk.services.codepipeline.model.ListPipelinesResponse;
import software.amazon.awssdk.services.codepipeline.model.PipelineSummary;

public class AWSCICDTool {

	public static void main(String[] args) {

		Region region = Region.US_EAST_1;

		listCodeDeployApplications(region);
		listCodePipelinePipelines(region);
	}

	public static void listCodeDeployApplications(Region region) {

		try (CodeDeployClient deployClient = CodeDeployClient.builder().region(region).build()) {
			ListApplicationsRequest request = ListApplicationsRequest.builder().build();
			ListApplicationsResponse response = deployClient.listApplications(request);

			System.out.println("=== CodeDeploy Applications ===");
			response.applications().forEach(app -> System.out.println(" - " + app));
		} catch (Exception e) {
			System.err.println("Error fetching CodeDeploy applications: " + e.getMessage());
		}
	}

	public static void listCodePipelinePipelines(Region region) {

		try (CodePipelineClient pipelineClient = CodePipelineClient.builder().region(region).build()) {
			ListPipelinesRequest request = ListPipelinesRequest.builder().build();
			ListPipelinesResponse response = pipelineClient.listPipelines(request);

			System.out.println("=== CodePipeline Pipelines ===");
			for (PipelineSummary pipeline : response.pipelines()) {
				System.out.println(" - " + pipeline.name() + " | Created: " + pipeline.created());
			}
		} catch (Exception e) {
			System.err.println("Error fetching CodePipeline pipelines: " + e.getMessage());
		}
	}
}
