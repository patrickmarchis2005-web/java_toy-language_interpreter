package repository;

import model.exception.MyException;
import model.state.ProgramState;

import java.io.IOException;
import java.util.List;

public interface Repository {
    void setProgramState(ProgramState state);
    void logProgramStateExec(ProgramState programState) throws MyException;
    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> programStateList);
}
