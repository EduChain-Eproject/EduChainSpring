package aptech.project.educhain.endpoint.controllers.courses.chapter.teacher;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.data.serviceImpl.courses.ChapterService;
import aptech.project.educhain.domain.dtos.courses.ChapterDTO;
import aptech.project.educhain.domain.useCases.courses.chapter.CreateChapterUsecase.CreateChapterParams;
import aptech.project.educhain.domain.useCases.courses.chapter.GetChapterDetailUsecase.GetChapterDetailParams;
import aptech.project.educhain.domain.useCases.courses.chapter.UpdateChapterUsecase.UpdateChapterParams;
import aptech.project.educhain.endpoint.requests.courses.chapter.teacher.CreateChapterRequest;
import aptech.project.educhain.endpoint.requests.courses.chapter.teacher.UpdateChapterRequest;
import aptech.project.educhain.endpoint.responses.courses.chapter.teacher.CreateChapterResponse;
import aptech.project.educhain.endpoint.responses.courses.chapter.teacher.GetChapterDetailResponse;
import aptech.project.educhain.endpoint.responses.courses.chapter.teacher.UpdateChapterResponse;

@RestController
@RequestMapping("/TEACHER/api/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/detail/{chapterId}")
    public ResponseEntity<?> getChapterDetail(@PathVariable Integer chapterId) {
        AppResult<ChapterDTO> result = chapterService.getChapterDetail(new GetChapterDetailParams(chapterId));
        if (result.isSuccess()) {
            var res = modelMapper.map(result.getSuccess(), GetChapterDetailResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createChapter(@RequestBody CreateChapterRequest request, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString()); // TODO
        }

        var chapter = chapterService.createChapter(modelMapper.map(request, CreateChapterParams.class));

        if (chapter.isSuccess()) {
            var res = modelMapper.map(chapter.getSuccess(), CreateChapterResponse.class);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(chapter.getFailure().getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/update/{chapterId}")
    public ResponseEntity<?> updateChapter(@PathVariable("chapterId") Integer chapterId,
            @RequestBody UpdateChapterRequest request, BindingResult rs) {
        if (rs.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            List<ObjectError> errorList = rs.getAllErrors();
            for (var err : errorList) {
                errors.append(err.getDefaultMessage()).append("\n");
            }
            return ResponseEntity.badRequest().body(errors.toString());// TODO
        }

        UpdateChapterParams params = modelMapper.map(request, UpdateChapterParams.class);
        params.setId(chapterId);

        var chapter = chapterService.updateChapter(params);

        if (chapter.isSuccess()) {
            var res = modelMapper.map(chapter.getSuccess(), UpdateChapterResponse.class);
            return ResponseEntity.ok().body(res);
        }
        return ResponseEntity.badRequest().body(chapter.getFailure().getMessage());
    }

    @DeleteMapping("/delete/{chapterId}")
    public ResponseEntity<?> deleteChapter(@PathVariable("chapterId") Integer chapterId) {
        var result = chapterService.deleteChapter(chapterId);
        if (result.isSuccess()) {
            return ResponseEntity.ok().body(chapterId);
        }
        return ResponseEntity.badRequest().body(result.getFailure().getMessage());
    }

}
