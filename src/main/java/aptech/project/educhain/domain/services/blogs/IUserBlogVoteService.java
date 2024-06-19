package aptech.project.educhain.domain.services.blogs;

import aptech.project.educhain.data.entities.blogs.Blog;

public interface IUserBlogVoteService {
    public Blog vote(Integer userId, Integer blogId, int vote);
}
