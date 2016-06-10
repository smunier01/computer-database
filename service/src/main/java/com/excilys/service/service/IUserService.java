package com.excilys.service.service;


import com.excilys.core.model.User;

import java.util.List;

public interface IUserService {

    /**
     * Return the list of all the users in the database.
     *
     * @return the list of the user
     */
    List<User> listAllUser();

    /**
     * Find a user with his id.
     *
     * @param id of the user to find
     * @return the User searched
     */
    User find(Integer id);

    /**
     * Search a user with his name.
     *
     * @param username of the user
     * @return a user
     */
    User findByName(String username);

    /**
     * Update the user in the data base.
     *
     * @param user to update
     * @return the user
     */
    User edit(User user);

    /**
     * Create a new user in the data base.
     *
     * @param user to insert.
     * @return the inserted user
     */
    User create(User user);

    /**
     * Remove the user in function of his id.
     *
     * @param id of the user to delete
     */
    void remove(int id);

    /**
     * Set the default value of the data base.
     */
    void defaultValues();
}
