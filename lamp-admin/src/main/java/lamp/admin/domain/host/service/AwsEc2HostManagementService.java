package lamp.admin.domain.host.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.util.Base64;
import lamp.admin.core.host.AwsCluster;
import lamp.admin.core.host.AwsEc2Host;
import lamp.admin.core.host.Host;
import lamp.admin.domain.host.service.form.AwsEc2HostsForm;
import lamp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AwsEc2HostManagementService {

	public List<AwsEc2Host> runInstances(AwsCluster cluster, AwsEc2HostsForm hostsForm) {
		AmazonEC2Client amazonEC2Client = createAmazonEC2Client(cluster);

		RunInstancesRequest runInstancesRequest =
			new RunInstancesRequest();

		runInstancesRequest.withImageId(cluster.getImageId())
			.withInstanceType(hostsForm.getInstanceType())
			.withMinCount(hostsForm.getMinCount())
			.withMaxCount(hostsForm.getMaxCount())
			.withSubnetId(hostsForm.getSubnetId())
			.withKeyName(hostsForm.getKeyName())
			.withSecurityGroupIds(hostsForm.getSecurityGroupIds());

		if (StringUtils.isNotBlank(hostsForm.getUserData())) {
			runInstancesRequest.withUserData(Base64.encodeAsString(hostsForm.getUserData().getBytes()));
		}


		RunInstancesResult runInstancesResult = amazonEC2Client.runInstances(runInstancesRequest);
		Reservation reservation = runInstancesResult.getReservation();
		List<Instance> ec2Instances = reservation.getInstances();

		List<AwsEc2Host> hosts = new ArrayList<>();
		for (Instance ec2Instance : ec2Instances) {
			log.info("ec2Instance = {}", ec2Instance);
			for (InstanceBlockDeviceMapping blockDeviceMapping : ec2Instance.getBlockDeviceMappings()) {
				log.info("blockDeviceMapping = {}", blockDeviceMapping);
			}

			AwsEc2Host host = new AwsEc2Host();
			host.setId(UUID.randomUUID().toString());
			host.setClusterId(cluster.getId());
			host.setClusterName(cluster.getName());
			host.setName(ec2Instance.getPrivateDnsName());
			host.setAddress(ec2Instance.getPrivateIpAddress());

			host.setPrivateDnsName(ec2Instance.getPrivateDnsName());
			host.setPrivateIpAddress(ec2Instance.getPrivateIpAddress());
			host.setPublicDnsName(ec2Instance.getPublicDnsName());
			host.setPublicIpAddress(ec2Instance.getPublicIpAddress());

			host.setInstanceId(ec2Instance.getInstanceId());
			hosts.add(host);
		}
		//		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		//		describeInstancesRequest.withInstanceIds("");
		//
		//		DescribeInstancesResult describeInstancesResult = amazonEC2Client.describeInstances(describeInstancesRequest);
		//		describeInstancesResult.getReservations().get(0).getInstances();

//		CreateTagsRequest createTagsRequest = new CreateTagsRequest();
//		createTagsRequest.withResources();
		return hosts;
	}

	public Instance getEC2Instance(AwsCluster cluster, String instanceId) {
		AmazonEC2Client amazonEC2Client = createAmazonEC2Client(cluster);
		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		describeInstancesRequest.withInstanceIds(instanceId);

		DescribeInstancesResult describeInstancesResult = amazonEC2Client.describeInstances(describeInstancesRequest);
		for  (Reservation reservation : describeInstancesResult.getReservations()) {
			for (Instance instance : reservation.getInstances()) {
				if (instanceId.equals(instance.getInstanceId())) {
					return instance;
				}
			}
		}
		return null;
	}

	protected AmazonEC2Client createAmazonEC2Client(AwsCluster cluster) {
		AWSCredentials credentials = new BasicAWSCredentials(cluster.getAccessKeyId(), cluster.getSecretAccessKey());

		AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
		amazonEC2Client.setEndpoint(cluster.getEc2Endpoint());
		return amazonEC2Client;
	}

	public void terminateInstances(AwsCluster cluster, AwsEc2Host host) {
		AmazonEC2Client amazonEC2Client = createAmazonEC2Client(cluster);
		String instanceId = host.getInstanceId();

		DescribeInstancesRequest describeInstancesRequest = new DescribeInstancesRequest();
		describeInstancesRequest.withInstanceIds(instanceId);

		Instance ec2Instance = null;
		DescribeInstancesResult describeInstancesResult = amazonEC2Client.describeInstances(describeInstancesRequest);
		for  (Reservation reservation : describeInstancesResult.getReservations()) {
			for (Instance instance : reservation.getInstances()) {
				if (instanceId.equals(instance.getInstanceId())) {
					ec2Instance = instance;
					break;
				}
			}
		}
		if (ec2Instance != null) {

//			EbsInstanceBlockDeviceSpecification ebsSpecification = new EbsInstanceBlockDeviceSpecification()
//				.withDeleteOnTermination(true);
//
//			InstanceBlockDeviceMappingSpecification mappingSpecification = new InstanceBlockDeviceMappingSpecification()
//				.withDeviceName(deviceName)
//				.withEbs(ebsSpecification);
//
//			ModifyInstanceAttributeRequest request = new ModifyInstanceAttributeRequest()
//				.withInstanceId(instanceId)
//				.withBlockDeviceMappings(mappingSpecification);
//
//			ec2Client.modifyInstanceAttribute(request);

			List<InstanceBlockDeviceMapping> blockDeviceMappings = ec2Instance.getBlockDeviceMappings();
			for (InstanceBlockDeviceMapping blockDeviceMapping : blockDeviceMappings) {
				log.info("blockDeviceMapping = {}", blockDeviceMapping);
			}

			TerminateInstancesRequest terminateInstancesRequest = new TerminateInstancesRequest();
			terminateInstancesRequest.withInstanceIds(ec2Instance.getInstanceId());
			TerminateInstancesResult terminateInstancesResult = amazonEC2Client.terminateInstances(terminateInstancesRequest);
			log.info("terminateInstancesResult : {}", terminateInstancesResult);

			terminateInstancesResult.getTerminatingInstances();
		}


	}
}
