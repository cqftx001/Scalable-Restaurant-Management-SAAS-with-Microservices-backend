/*
 * <b>文件名</b>：OssAliyunFileStorageService.java
 *
 * 文件描述：
 *
 *
 * 2018年12月17日  下午4:27:10
 */

package com.itheima.restkeeper.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.itheima.restkeeper.properties.OssAliyunConfigProperties;
import com.itheima.restkeeper.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @Description 阿里Oss文件上传
 */
@Slf4j
@Component
@EnableConfigurationProperties(OssAliyunConfigProperties.class)
public class OSSAliyunFileStorageService implements FileStorageService {

	@Autowired
	OSS ossClient;

	@Autowired
	OssAliyunConfigProperties ossAliyunConfigProperties;

	public String builderOssPath(String dirPath,String filename) {
		String separator = "/";
		StringBuilder stringBuilder = new StringBuilder(50);
		LocalDate localDate = LocalDate.now();
		String yeat = String.valueOf(localDate.getYear());
		stringBuilder.append(yeat).append(separator);
		String moth = String.valueOf(localDate.getMonthValue());
		stringBuilder.append(moth).append(separator);
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		String day = formatter.format(localDate);
		stringBuilder.append(day).append(separator);
		stringBuilder.append(filename);
		return stringBuilder.toString();
	}

	/* (non-Javadoc)
	 * @see FileStorageService#store(java.lang.String, java.lang.String, java.io.InputStream)
	 */
	@Override
	public String store(String prefix, String filename, InputStream inputStream) {
		//文件读取路径
		String url = null;
		// 设置文件路径和名称（Key）
		String key = builderOssPath(prefix, filename);
		// 判断文件
		if (inputStream==null) {
			log.error("上传文件：key{}文件流为空",key);
			return url;
		}
		log.info("OSS文件上传开始：{}" ,key);
		try {
			String bucketName = ossAliyunConfigProperties.getBucketName();
			// 上传文件
			ObjectMetadata objectMeta = new ObjectMetadata();
		    objectMeta.setContentEncoding("UTF-8");
			objectMeta.setContentType("image/jpg");
		    PutObjectRequest request = new PutObjectRequest(bucketName, key, inputStream,objectMeta);
			PutObjectResult result = ossClient.putObject(request);
			// 设置权限(公开读)
			ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
			if (result != null) {
				log.info("OSS文件上传成功：{}" ,key);
	            url = key.toString();
			}
		} catch (OSSException oe) {
			log.error("OSS文件上传错误：{}", oe);
		} catch (ClientException ce) {
			log.error("OSS文件上传客户端错误：{}",ce);
		}
		return url;
	}

	/* (non-Javadoc)
	 * @see FileStorageService#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String pathUrl) {
		String key = pathUrl;
		List<String> keys = Lists.newArrayList();
		keys.add(key);
		// 删除Objects
        ossClient.deleteObjects(new DeleteObjectsRequest(ossAliyunConfigProperties.getBucketName()).withKeys(keys));

	}

	@Override
	public void deleteBatch(List<String> pathUrls) {
		// 删除Objects
		ossClient.deleteObjects(new DeleteObjectsRequest(ossAliyunConfigProperties.getBucketName()).withKeys(pathUrls));
	}

	/* (non-Javadoc)
	 * @see FileStorageService#downloadFile(java.lang.String)
	 */
	@Override
	public InputStream downloadFile(String pathUrl) throws IOException {
		// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
		OSSObject ossObject = ossClient.getObject(ossAliyunConfigProperties.getBucketName(), pathUrl);
		InputStream inputStream =  ossObject.getObjectContent();
        return inputStream;
	}

	/* (non-Javadoc)
	 * @see FileStorageService#getFileContent(java.lang.String)
	 */
	@Override
	public String getFileContent(String pathUrl) throws IOException {
		InputStream inputStream = downloadFile(pathUrl);
		return new String(ByteStreams.toByteArray(inputStream));
	}

}
