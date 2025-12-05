using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Aracil_Victor_GestionTareas03._01
{
    public class Etiqueta
    {
        [Key]
        public int Id { get; set; }
        public string Nombre { get; set; }

        // Relación 1:N con Tarea
        [InverseProperty("Etiqueta")] //Una misma etiqueta puede estar en muchas tareas es la relacion N
        public virtual ICollection<Tarea> Tareas { get; set; } = new List<Tarea>();
    }

}
