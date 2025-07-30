package com.aws.codedeploy.codepipeline;

import software.amazon.awssdk.services.codepipeline.CodePipelineClient;
import software.amazon.awssdk.services.codepipeline.model.ListPipelinesRequest;
import software.amazon.awssdk.services.codepipeline.model.ListPipelinesResponse;
import software.amazon.awssdk.services.codepipeline.model.PipelineSummary;
import software.amazon.awssdk.regions.Region;

public class AWSCICDTool {

	public static void main(String[] args) {
		
		AWSCICDTool tool = new AWSCICDTool();
		tool.listFirstCodePipeline(Region.US_EAST_1); // Or any valid region
	}

	public static String listFirstCodePipeline(Region region) {

		try (CodePipelineClient cpClient = CodePipelineClient.builder().region(region).build()) {

			ListPipelinesRequest request = ListPipelinesRequest.builder().build();
			ListPipelinesResponse response = cpClient.listPipelines(request);

			if (!response.pipelines().isEmpty()) {
				PipelineSummary pipeline = response.pipelines().get(0);
				System.out.println("First Pipeline Name: " + pipeline.name());
				return pipeline.name();
			} else {
				System.out.println("No pipelines found.");
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Make sure you also have listFirstCodeDeployApp(Region) if you're testing that
	// too
	public static String listFirstCodeDeployApp(Region region) {
		// Dummy return for now — implement similarly using CodeDeployClient
		return "SampleApp";
	}
}
