package com.springboot;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.then;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class SpringBootFileUploadApplicationTests {

	@Autowired
	private MockMvc mock;

	@MockBean
	private StorageService storageService;

	@Test
	public void saveUploadedFileTest() throws Exception {
		MockMultipartFile mockMultipart = new MockMultipartFile("file", "text.txt", "text/plain", "Spring Framework".getBytes());
		this.mock.perform(fileUpload("/").file(mockMultipart))
				.andExpect(status().isFound())
				.andExpect(header().string("Location", "/"));
		then(this.storageService).should().store(mockMultipart);
	}

}
