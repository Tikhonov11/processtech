/*
 * This file is part of the RUNA WFE project.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; version 2.1
 * of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wf.web.action;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.runa.common.web.action.ActionBase;
import ru.runa.wfe.commons.TypeConversionUtil;
import ru.runa.wfe.service.delegate.Delegates;
import ru.runa.wfe.var.dto.WfVariable;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.net.MediaType;

/**
 * Created on 23.10.2015
 *
 * @struts:action path="/variableJson" name="variableForm" validate="false"
 */
public class VariableJsonAction extends ActionBase {
    public static final String ACTION_PATH = "/variable";

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
        try {
            String variableName = request.getParameter("variableName");
            Preconditions.checkNotNull(variableName, "variableName");
            Long processId = TypeConversionUtil.convertTo(Long.class, request.getParameter("processId"));
            Long taskId = TypeConversionUtil.convertTo(Long.class, request.getParameter("taskId"));
            Preconditions.checkArgument(processId != null || taskId != null, "!processId && !taskId");
            if (processId == null) {
                processId = Delegates.getTaskService().getTask(getLoggedUser(request), taskId).getProcessId();
            }
            response.setContentType(MediaType.JSON_UTF_8.toString());
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "max-age=0");
            OutputStream os = response.getOutputStream();
            WfVariable variable;
            if (taskId != null) {
                variable = Delegates.getExecutionService().getTaskVariable(getLoggedUser(request), processId, taskId, variableName);
            } else {
                variable = Delegates.getExecutionService().getVariable(getLoggedUser(request), processId, variableName);
            }
            String json = variable != null ? variable.getStringValue() : "null";
            os.write(json.getBytes(Charsets.UTF_8));
            os.flush();
        } catch (Exception e) {
            log.error("", e);
            try {
                response.sendError(500);
            } catch (IOException e1) {
                log.warn(e1);
            }
        }
        return null;
    }

}
