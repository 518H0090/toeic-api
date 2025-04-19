package com.toeic.question.adapter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toeic.question.application.dto.add.AddParagraphDto;
import com.toeic.question.application.dto.update.UpdateParagraphDto;
import com.toeic.question.application.service.IParagraphService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/paragraphs")
@RequiredArgsConstructor
public class ParagraphController {

    private final IParagraphService _paragraphService;

    @GetMapping
    public ResponseEntity<?> GetParagraphs() {
        var paragraphs = _paragraphService.getParagraphs();
        return new ResponseEntity<>(paragraphs, HttpStatus.OK);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<?> GetParagraphById(@PathVariable("id") int paragraphId) {
        var paragraph = _paragraphService.getParagraphById(paragraphId);
        return new ResponseEntity<>(paragraph, HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> AddParagraph(@RequestBody @Valid AddParagraphDto request) {
        var addedParagraph = _paragraphService.addParagraph(request);
        return new ResponseEntity<>(addedParagraph, HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<?> UpdateParagraph(@RequestBody @Valid UpdateParagraphDto request) {
        _paragraphService.updateParagraph(request);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> DeleteParagraph(@PathVariable("id") Integer paragraphId) {
        _paragraphService.deleteParagraph(paragraphId);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
