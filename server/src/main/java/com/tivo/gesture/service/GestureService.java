package com.tivo.gesture.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.tivo.gesture.controller.model.ChannelInput;
import com.tivo.gesture.controller.model.GestureInput;
import com.tivo.gesture.controller.model.Response;

@Service
public class GestureService {
	private int secCounter = 0;

	@Value("${video.url}")
	private String[] videoLinks;

	private int current = 0;

	@Scheduled(fixedRate = 1000)
	public void scheduleTaskWithFixedRate() {
		secCounter++;
	}

	public Response getResponse(GestureInput model) {
		// TODO Auto-generated method stub
		Response response = new Response();
		response.setUrl("abcd");
		return response;
	}

	public Response changechannel(GestureInput product) {

		int length = videoLinks.length;
		String direction = product.getDirection();
		if ("right".equalsIgnoreCase(direction)) {
			current = (current + 1) % length;
		} else if ("left".equalsIgnoreCase(direction)) {
			if (current == 0) {
				current = length - 1;
			} else {
				current = current - 1;
			}
		}
		Response response = new Response();
		String[] split = videoLinks[current].split(":");
		String url = split[0];
		int sec = secCounter % (Integer.parseInt(split[1]));

		response.setUrl(url);
		response.setSeconds(sec);
		return response;
	}

	public Response getchannel(ChannelInput product) {
		Response response = new Response();
		for (int i = 0; i < videoLinks.length; i++) {
			String[] split = videoLinks[i].split(":");
			String video = split[0];
			boolean b = video.equalsIgnoreCase(product.getChannelName() + ".mp4");
			if (b) {
				String s = "http://10.177.65.71:8080/videos/" + video;
				response.setUrl(s);

				int sec = secCounter % (Integer.parseInt(split[1]));
				response.setSeconds(sec);
				return response;
			}
		}
		return response;
	}

}
