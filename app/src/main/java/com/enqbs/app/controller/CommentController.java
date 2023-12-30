package com.enqbs.app.controller;

import com.enqbs.app.form.ProductCommentForm;
import com.enqbs.app.form.ProductCommentReplyForm;
import com.enqbs.app.service.product.ProductCommentReplyService;
import com.enqbs.app.service.product.ProductCommentService;
import com.enqbs.common.exception.ServiceException;
import com.enqbs.common.util.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private ProductCommentService productCommentService;

    @Resource
    private ProductCommentReplyService productCommentReplyService;

    @PostMapping
    public R<Void> addComment(@Valid @RequestBody ProductCommentForm form) {
        int row = productCommentService.insert(form);

        if (row <= 0) {
            throw new ServiceException("评价失败");
        }

        return R.ok("评价成功");
    }

    @PutMapping("/{commentId}")
    public R<Void> updateComment(@PathVariable Integer commentId, @Valid @RequestBody ProductCommentForm form) {
        int row = productCommentService.update(commentId, form);

        if (row <= 0) {
            throw new ServiceException("修改评价失败");
        }

        return R.ok("修改评价成功");
    }

    @DeleteMapping("/{commentId}")
    public R<Void> deleteComment(@PathVariable Integer commentId) {
        int row = productCommentService.delete(commentId);

        if (row <= 0) {
            throw new ServiceException("删除评价失败");
        }

        return R.ok("删除评价成功");
    }

    @PostMapping("/reply")
    public R<Void> addCommentReply(@Valid @RequestBody ProductCommentReplyForm form) {
        int row = productCommentReplyService.insert(form);

        if (row <= 0) {
            throw new ServiceException("回复失败");
        }

        return R.ok("回复成功");
    }

    @DeleteMapping("/reply/{replyId}")
    public R<Void> deleteCommentReply(@PathVariable Integer replyId) {
        int row = productCommentReplyService.delete(replyId);

        if (row <= 0) {
            throw new ServiceException("删除回复失败");
        }

        return R.ok("删除回复成功");
    }

}
