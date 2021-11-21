package com.vitalhub.tokyoolympicmanagementapp.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.transaction.Transactional;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.vitalhub.tokyoolympicmanagementapp.dto.ImageDTO;
import com.vitalhub.tokyoolympicmanagementapp.entity.Image;
import com.vitalhub.tokyoolympicmanagementapp.mapper.GeneralMapper;
import com.vitalhub.tokyoolympicmanagementapp.repository.ImageRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final GeneralMapper generalMapper;
	
	@Autowired
	public ImageService(ImageRepository imageRepository, GeneralMapper generalMapper) {
		this.imageRepository = imageRepository;
		this.generalMapper = generalMapper;
	}
	
	public List<ImageDTO> getImages() {
		return imageRepository.findAll().stream().map(image -> generalMapper.imageToImageDTO(image)).toList();
	}
	
	public ImageDTO getImageById(Long imageId) {
		return generalMapper.imageToImageDTO(imageRepository.findById(imageId).get());
	}
	
	public ImageDTO addImage(ImageDTO imageDTO) {
		return generalMapper.imageToImageDTO(imageRepository.save(generalMapper.imageDTOTOImage(imageDTO)));
	}
	
	@Transactional
	public ImageDTO updateImage(ImageDTO imageDTO, Long imageId) {
		Image existingImageRecord = imageRepository.findById(imageId)
				.orElseThrow(() -> new EmptyResultDataAccessException("Image with " + imageId + " doesn't exist.", 0));
		
		Image updatedImage = generalMapper.imageDTOTOImage(imageDTO);
		if(!existingImageRecord.equals(null)) {
			existingImageRecord.setData(updatedImage.getData());
			existingImageRecord.setName(updatedImage.getName());
			existingImageRecord.setType(updatedImage.getType());
		}
		return generalMapper.imageToImageDTO(existingImageRecord);
	}
	
	
	public void deleteImage(Long imageId) {
		Boolean imageExists = imageRepository.existsById(imageId);
		if(!imageExists) {
			throw new EmptyResultDataAccessException(0);
		} else {
			imageRepository.deleteById(imageId);
		}
	}
	
	/** 
	 * Compress the image bytes before storing in the database.
	 * 
	 * @param data bytes of the file to be compressed. Class Deflater is used to compress the data. 
	 * @return	a compressed byte array.
	 * @throws FileUploadException is thrown if an IOException is thrown from the ByteArrayOutputStream or the Deflater operations.
	 */
	public byte[] compressFileToBytes(byte[] data) throws FileUploadException {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[2048];
		while(!deflater.finished()) {
			int count = deflater.deflate(buffer);
			byteArrayOutputStream.write(buffer, 0, count);
		}
		try {
			byteArrayOutputStream.close();
		} catch (IOException ioException) {
			log.info("IOException while compressing the file", ioException.getMessage());
			throw new FileUploadException("IOException whilte converting the file into bytes");
		}
		return byteArrayOutputStream.toByteArray();
	}
	
	/**
	 * 
	 * @param data bytes of the file to be decompressed. Inflator decompressed the byte array passed from the athlete
	 * 			record extracted from the database.
	 * @return a decompressed byte array. 
	 * @throws DataFormatException is thrown if the Inflator operation fails while decompossing the file. 
	 */
	public byte[] decompressBytes(byte[] data) throws DataFormatException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[2048];
		try {
			while(!inflater.finished()) {
				int count = inflater.inflate(buffer);
				byteArrayOutputStream.write(buffer, 0, count);
			}
		} catch (DataFormatException dataFormatException) {
			log.info("Data Format exception whilte decompressing the file.", dataFormatException.getMessage());
			throw new DataFormatException("Data Format exception whilte decompressing the file.");
		}
		return byteArrayOutputStream.toByteArray();
	}
}
